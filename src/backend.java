import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.SSLSession;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;



public class backend {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        int type = 0;
        do {
            System.out.println("Would you like to search by subject(1), tags(2), or quit(3):");
            type = scan.nextInt();
            scan.nextLine();
            if (type == 1) {
                System.out.println("Enter the subject:");
                String userInput = scan.nextLine();
                searchBySubject(userInput);
            } else if (type == 2) {
                System.out.println("Enter the tag:");
                String req = scan.nextLine();
                System.out.println("Enter a subject to specify the search or press enter to see all courses for this requirement:");
                String sub = scan.nextLine();
                searchByReq(req, sub);
            } else {
                System.out.println("Please enter a valid input");
            }
        } while (type != 3);
    }

    public static void searchBySubject(String userInput) throws IOException, InterruptedException {
        while (userInput.indexOf(' ') != -1) {
            userInput = userInput.substring(0, userInput.indexOf(' ')) + "%20" + userInput.substring(userInput.indexOf(' ') + 1);
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response = new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        HttpRequest requestA;
        HttpResponse<String> responseA = new HttpResponse<String>() {
            @Override
            public int statusCode() {
                return 0;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.purdue.io/odata/Courses?$filter=contains(Title,%20%27"+ userInput +"%27)"))
                    .build();
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            requestA = HttpRequest.newBuilder()
                    .uri(URI.create("http://api.purdue.io/odata/Subjects"))
                    .build();
            responseA = client.send(requestA,
                    HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
        }

        org.json.JSONObject json = new JSONObject(response.body());
        org.json.JSONObject jsonA = new JSONObject(responseA.body());

        if(response.equals(null)) {
            System.out.println("Error!");
        }
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < json.getJSONArray("value").length(); i++) {
            String subjectId = json.getJSONArray("value").getJSONObject(i).getString("SubjectId");
            String courseName = json.getJSONArray("value").getJSONObject(i).getString("Title");
            String number = json.getJSONArray("value").getJSONObject(i).getString("Number");
            for (int j = 0; j < jsonA.getJSONArray("value").length(); j++) {
                if (subjectId.equals(jsonA.getJSONArray("value").getJSONObject(j).getString("SubjectId"))) {
                    String abbrev = jsonA.getJSONArray("value").getJSONObject(j).getString("Abbreviation");
                    Course course = new Course(courseName, number, abbrev);
                    getTags(course);
                    courses.add(course);
                    break;
                }
            }
        }
        for(Course c : courses) {
            System.out.println(c.toString());
        }
    }

    public static void searchByReq(String req, String sub) {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(
                    new FileReader("/Users/Rohan/IdeaProjects/helloWorldHackathon/src/requirements.json"));
            org.json.simple.JSONObject subObj = (org.json.simple.JSONObject) obj.get(req);
            org.json.simple.JSONArray subObjArray = (org.json.simple.JSONArray) subObj.get("courses");
            for (Object o : subObjArray) {
                String s = (String) o;
                if (s.indexOf(sub) != -1) {
                    String abbrev = s.substring(0, s.indexOf(' '));
                    s = s.substring(s.indexOf(' ') + 1);
                    String number = s.substring(0, s.indexOf(' '));
                    s = s.substring(s.indexOf(' ') + 1);
                    String title = s;
                    Course course = new Course(title, number, abbrev);
                    getTags(course);
                    courses.add(course);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Course c : courses) {
            System.out.println(c.toString());
        }
    }

    public static void getTags(Course course) {
        JSONParser parser = new JSONParser();
        try {
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) parser.parse(
                    new FileReader("/Users/Rohan/IdeaProjects/helloWorldHackathon/src/requirements.json"));

            Iterator<String> keys = obj.keySet().iterator();

            while(keys.hasNext()) {
                String key = keys.next();
                // do something with jsonObject here
                org.json.simple.JSONObject subObj = (org.json.simple.JSONObject) obj.get(key);
                org.json.simple.JSONArray subObjArray = (org.json.simple.JSONArray) subObj.get("courses");
                for (Object o : subObjArray) {
                    String s = (String) o;
                    if(s.equals(course.getOfficialCourse())) {
                        course.add(key);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}