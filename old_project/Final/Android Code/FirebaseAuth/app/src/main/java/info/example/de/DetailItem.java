package info.example.de;

/**
 * The {@link DetailItem} class.
 * <p>Defines the attributes for a restaurant menu item.</p>
 */

public class DetailItem {

    private final String event_id;
    private final String conntact_number;
    private final String name;
    private final String status_call;
    private final String confirm_status;

    public DetailItem(String event_id , String conntact_number,
                      String name, String status_call,String confirm_status) {
        this.event_id = event_id;
        this.conntact_number = conntact_number;
        this.name = name;
        this.status_call = status_call;
        this.confirm_status = confirm_status;
    }

    public String get_event_id() {
        return event_id;
    }
    public String get_conntact_number() {
        return conntact_number;
    }
    public String get_name() {
        return name;
    }
    public String get_status_call() {
        return status_call;
    }
    public String get_confirm_status() {
        return confirm_status;
    }
}
