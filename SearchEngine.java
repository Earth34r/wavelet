import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // An ArrayList of Strings that is manipulated by calls to the server
    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<String> found = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Use /search to search!, or /add to add strings!");
        }
       
	else if (url.getPath().contains("/search")) {
	    String[] parameters = url.getQuery().split("=");
	    if (parameters[0].equals("s")) {
		for (String s : strings) {
		    if (s.contains(parameters[1])) {
		        found.add(s);
		    }
		}
		return String.format("Found Strings: %s", found.toString());
	    }
        }
       
	else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
		    strings.add(parameters[1]);
		    return String.format("String %s added successfully", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
	return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
