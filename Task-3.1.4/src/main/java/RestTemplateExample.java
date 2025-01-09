import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestTemplateExample {
    public static void main(String[] args) {
        String url = "http://94.198.50.185:7081/api/users";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class
        );

        String sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println("Session ID: " + sessionId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        User newUser = new User(3L, "James", "Brown", (byte) 30);
        HttpEntity<User> requestEntity = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> addResponse = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        System.out.println("Add Response: " + addResponse.getBody());

        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> updateRequestEntity = new HttpEntity<>(updatedUser, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                updateRequestEntity,
                String.class
        );

        System.out.println("Update Response: " + updateResponse.getBody());

        HttpEntity<String> deleteRequestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                url + "/3",
                HttpMethod.DELETE,
                deleteRequestEntity,
                String.class
        );

        System.out.println("Delete Response: " + deleteResponse.getBody());

        String resultCode = addResponse.getBody() + updateResponse.getBody() + deleteResponse.getBody();
        System.out.println("Result Code: " + resultCode);
    }
}


