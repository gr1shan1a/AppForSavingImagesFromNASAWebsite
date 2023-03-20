import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    //option + shift множественный курсор
    //option + cmd + l форматирование
    public static final String url = "https://api.nasa.gov/planetary/apod?api_key=meXuPit01arAzczsziwyU97LKABrdqxjYWmoy8e9";

    public static final ObjectMapper mapper = new ObjectMapper(); // специальный экземпляр класса

    // ObjectMapper(объект), который переводит
    // JSON в объекты Java

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build()) // собирает config
                .build(); // собирает client

        CloseableHttpResponse response = httpClient.execute(new HttpGet(url)); // отправил запрос

//        Scanner sc = new Scanner(response.getEntity().getContent());
//        System.out.println(sc.nextLine());

        nasa NASA = mapper.readValue(response.getEntity().getContent(), nasa.class);
        CloseableHttpResponse image = httpClient.execute(new HttpGet(NASA.getUrl()));

        HttpEntity entity = image.getEntity();
        if (entity != null) {
            FileOutputStream fos = new FileOutputStream("image.jpg"); //файловый дискриптор, доступ до закрытия запрещен.
            entity.writeTo(fos);
            fos.close();
        }
    }
} // auto-formatting cmd + alt + l
