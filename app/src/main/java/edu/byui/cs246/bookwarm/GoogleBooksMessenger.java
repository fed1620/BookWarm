package edu.byui.cs246.bookwarm;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jake on 3/20/2015.
 */
public class GoogleBooksMessenger extends AsyncTask {
    String googleURL;
    String BookWarmAPIKey;
    String charset;
    String serverResponse;
    String query;

    /**
     * Mostly here for test purposes.
     * @return The string version of the server's response
     */
    public String getServerResponse() {
        return serverResponse;
    }

    /**
     * Default constructor. Mostly just using it to initialize variables
     */
    public GoogleBooksMessenger(){
        googleURL      = "https://www.googleapis.com/books/v1/volumes?q=";
        BookWarmAPIKey = "AIzaSyBui4WguBN38J7ou6BzZ2iAndzwEuOxgQU"; //gross
        charset        = "UTF-8";
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            serverResponse = getResponse(query).toString();

            //test prints
            //TODO: Get rid of this eventually
            System.out.println("SERVER RESPONSE..............");
            System.out.println(serverResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * The 'main' of this function.
     * TODO: Complete this header and make sure it returns something meaningful
     * @param query A string representing whatever the user inputted for a search. Does not need prior modification.
     */
    public void searchGoogleBooks(String query) {
        this.query = query;

        //arbitraty variable - we only need it so we can call doInBackground
        Object[] params = new Object[0];
        doInBackground(params);
    }

    /**
     * Makes the actual connection to the server.
     * @param query the user's unmodified search term
     * @return the response from the server
     * @throws IOException A general derpage indicator.
     */
    private InputStream getResponse(String query) throws IOException {
        //building the connection
        URLConnection connection = new URL(buildString(query)).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);

        //actually getting the response

        return connection.getInputStream();
    }

    /**
     * Trivial function to build a proper query string
     * @param query the user's unmodified search term
     * @return a send-ready query string
     */
    private String buildString(String query) {
        query = query.replace(' ', '+');
        return googleURL + query + ":keyes&key=" + BookWarmAPIKey;
    }
}
