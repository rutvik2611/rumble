package info.example.de;

/**
 * The {@link ProjectItem} class.
 * <p>Defines the attributes for a restaurant menu item.</p>
 */
public class ProjectItem {

    private final String email;
    private final String message;
    private final String event_date;
    private final String event_id;

    public ProjectItem(String email , String message,
                       String event_date,String event_id) {
        this.email = email;
        this.message = message;
        this.event_date = event_date;
        this.event_id = event_id;
    }

    public String get_email() {
        return email;
    }


    public String get_message() {
        return message;
    }


    public String get_event_date() {
        return event_date;
    }


    public String get_event_id() {
        return event_id;
    }

}
