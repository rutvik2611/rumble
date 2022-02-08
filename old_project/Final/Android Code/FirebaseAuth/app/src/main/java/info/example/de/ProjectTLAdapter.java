package info.example.de;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ProjectTLAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int BANNER_AD_VIEW_TYPE = 1;
    private Context context;
    private final List<Object> recyclerViewItems;


    public ProjectTLAdapter(Context context, List<Object> recyclerViewItems) {
        this.context = context;
        this.recyclerViewItems = recyclerViewItems;
    }


    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView menuItemEmail;
        private TextView menuItemMessage;
        private TextView menuItemId;
        private TextView menuItemDate;

        MenuItemViewHolder(final View view) {

            super(view);
            context = view.getContext();
            menuItemEmail = view.findViewById(R.id.user_email);
            menuItemMessage = view.findViewById(R.id.message);
            menuItemId = view.findViewById(R.id.event_id);
            menuItemDate = view.findViewById(R.id.event_date);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProjectItem pitem = (ProjectItem) recyclerViewItems.get(getPosition());
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("event_id", pitem.get_event_id());
                    intent.putExtra("email", pitem.get_email());
                    intent.putExtra("event_date", pitem.get_event_date());
                    context.startActivity(intent);
                }
            });
        }
    }


    public class AdViewHolder extends RecyclerView.ViewHolder {

        AdViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return recyclerViewItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return MENU_ITEM_VIEW_TYPE;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            default:
                View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.user_project_item, viewGroup, false);
                return new MenuItemViewHolder(menuItemLayoutView);

        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            default:
                MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;
                ProjectItem pitem = (ProjectItem) recyclerViewItems.get(position);
                RequestOptions options = new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(600,400);

                menuItemHolder.menuItemEmail.setText(pitem.get_email());
                menuItemHolder.menuItemMessage.setText(pitem.get_message());
                menuItemHolder.menuItemId.setText(pitem.get_event_id());
                menuItemHolder.menuItemDate.setText(pitem.get_event_date());


                break;
        }
    }

}
