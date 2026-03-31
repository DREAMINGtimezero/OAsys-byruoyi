package com.ruoyi.web.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    private static final String FILE_DELIMETER = ",";

    /**
     * @Description  跳转到文件预览页面
     * @Author wangshilin
     * @Date 2025/8/15 17:35
     **/
    @GetMapping("/preview")
    public String preview(String fileNames, ModelMap modelMap){
        modelMap.put("fileNames", fileNames);
        return "file-preview";
    }

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 自定义可多个文件同时下载成压缩包
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/batchDownload")
    public void batchDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try
        {
            // 检查文件名是否包含逗号，如果包含则处理多个文件
            if (fileName.contains(",")) {
                String[] fileNames = fileName.split(",");
                log.info("开始批量下载，文件数量: {}", fileNames.length);
                // 验证所有文件名
                for (String name : fileNames) {
                    if (!FileUtils.checkAllowDownload(name)) {
                        throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", name));
                    }
                }
                // 设置压缩包响应头
                String zipFileName = System.currentTimeMillis() + ".zip";
                response.setContentType("application/zip");
                FileUtils.setAttachmentResponseHeader(response, zipFileName);
                try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
                    int fileCount = 0;
                    // 遍历所有文件，添加到压缩包
                    for (String name : fileNames) {
                        String filePath = RuoYiConfig.getUploadPath() + name;
                        File file = new File(filePath);
                        log.info("尝试添加文件到压缩包: {}", filePath);
                        // 确保文件名处理逻辑安全
                        String entryName = name.substring(name.lastIndexOf("/") + 1, name.lastIndexOf("_"))
                                                                            + name.substring(name.lastIndexOf("."));
                        // 添加文件到压缩包
                        zipOut.putNextEntry(new ZipEntry(entryName));
                        try (FileInputStream fis = new FileInputStream(file)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                zipOut.write(buffer, 0, bytesRead);
                            }
                            fileCount++;
                            log.info("成功添加文件: {}, 大小: {} bytes", filePath, file.length());
                        }
                        zipOut.closeEntry();
                        if (delete != null && delete) {
                            FileUtils.deleteFile(filePath);
                            log.info("已删除源文件: {}", filePath);
                        }

                    }
                    if (fileCount == 0) {
                        log.error("压缩包为空，没有找到任何有效文件");
                        throw new Exception("没有找到任何有效文件");
                    }
                    log.info("批量下载完成，共添加 {} 个文件到压缩包", fileCount);
                }
            } else {
                // 单文件下载逻辑保持不变
                if (!FileUtils.checkAllowDownload(fileName))
                {
                    throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
                }
                String entryName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("_"))
                        + fileName.substring(fileName.lastIndexOf("."));
                String filePath = RuoYiConfig.getUploadPath() + fileName;
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                FileUtils.setAttachmentResponseHeader(response, entryName);
                FileUtils.writeBytes(filePath, response.getOutputStream());
                if (delete != null && delete)
                {
                    FileUtils.deleteFile(filePath);
                }
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败: {}", e.getMessage(), e);
            // 设置错误响应
            response.reset();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("下载失败: " + e.getMessage());
            } catch (IOException ex) {
                log.error("设置错误响应失败", ex);
            }
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ResponseBody
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception {
        try{
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + FileUtils.stripPrefix(resource);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * @Description  文件预览
     * @Author wangshilin
     * @Date 2025/8/18 09:57
     **/
    @GetMapping("/previewFile")
    public ResponseEntity<byte[]> previewFile(@RequestParam String fileName) {
        try {
            // 假设文件存储在本地路径（需根据实际存储调整，如 FTP/OSS）
            String filePath = RuoYiConfig.getUploadPath() + fileName;
            File file = new File(filePath);
            byte[] bytes = Files.readAllBytes(file.toPath());

            // **动态获取文件类型**（关键：根据文件名后缀设置 Content-Type）
            String contentType = determineContentType(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 辅助方法：根据文件名后缀判断 Content-Type
    private String determineContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (fileName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        return "application/octet-stream"; // 默认二进制流
    }
}
