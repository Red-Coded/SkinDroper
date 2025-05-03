package me.trex.skindroper.skinloader.armour;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GitHubReleaseDownloader {

    public static void latest(String[] args) {
        String url = "api.github.com/repos/Ancientkingg/fancyPants/releases/latest";
        String downloadUrl = getDownloadUrl(url);
        if (downloadUrl != null) {
            downloadFile(downloadUrl, "fancyPants.template.RP.1_21_3.zip");
        }
    }

    public static void older(String[] args) {
        String url = "api.github.com/repos/Ancientkingg/fancyPants/releases/110950061";
        String downloadUrl = getDownloadUrl(url);
        if (downloadUrl != null) {
            downloadFile(downloadUrl, "fancyPantsv3_0_0.zip");
        }
    }

    private static String getDownloadUrl(String apiUrl) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            request.addHeader("Accept", "application/vnd.github.v3+json");
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                // Parse JSON to get the download URL (simplified for brevity)
                // You might want to use a library like Jackson or Gson for JSON parsing
                String downloadUrl = extractDownloadUrl(result);
                return downloadUrl;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String extractDownloadUrl(String json) {
        // Simplified extraction logic
        int start = json.indexOf("browser_download_url\":\"") + 22;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private static void downloadFile(String fileUrl, String fileName) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(fileUrl);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent();
                     FileOutputStream outputStream = new FileOutputStream(fileName)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

