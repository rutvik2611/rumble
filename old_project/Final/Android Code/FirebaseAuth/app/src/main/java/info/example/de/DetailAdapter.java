package info.example.de;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private final List<Object> recyclerViewItems;


    public DetailAdapter(Context context, List<Object> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
    }


    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView event_id;
        private TextView conntact_number;
        private TextView name;
        private TextView status_call;
        private TextView confirm_status;

        MenuItemViewHolder(final View view) {

            super(view);
            context = view.getContext();
            event_id = view.findViewById(R.id.event_id);
            conntact_number = view.findViewById(R.id.conntact_number);
            name = view.findViewById(R.id.name);
            status_call = view.findViewById(R.id.status_call);
            confirm_status = view.findViewById(R.id.confirm_status);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.user_detail_item, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);

        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                DetailItem pitem = (DetailItem) recyclerViewItems.get(position);
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(600,400);

                menuItemHolder.event_id.setText(pitem.get_event_id());
                menuItemHolder.conntact_number.setText(pitem.get_conntact_number());
                menuItemHolder.name.setText(pitem.get_name());
                if(pitem.get_status_call().equals("1")){
                    menuItemHolder.status_call.setText("Done");
                }else{
                    menuItemHolder.status_call.setText("Not Done");
                }

                switch (pitem.get_confirm_status()) {
                    case "0":
                        menuItemHolder.confirm_status.setText("Pending");
                        break;
                    case "1":
                        menuItemHolder.confirm_status.setText("Coming");
                        break;
                    case "2":
                        menuItemHolder.confirm_status.setText("Not Coming");
                        break;
                }
                break;
        }
    }

}
