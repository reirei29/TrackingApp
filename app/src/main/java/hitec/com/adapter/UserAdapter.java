package hitec.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hitec.com.R;
import hitec.com.model.UserItem;
import hitec.com.ui.HomeActivity;
import hitec.com.util.SharedPrefManager;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private HomeActivity parent;
    private int usertype;
    private List<UserItem> items = new ArrayList<>();

    public UserAdapter(HomeActivity parent) {
        this.parent = parent;
        this.usertype = SharedPrefManager.getInstance(parent).getUserType();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_users, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        UserItem item = items.get(position);

        holder.tvUserName.setText(item.getUsername());

        holder.view.setTag(position);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int)view.getTag();
                UserItem item = items.get(position);
                Log.v("Selected User", item.getUsername());
                /*Intent intent = new Intent(parent, BranchDetailActivity.class);
                intent.putExtra("item", item);
                parent.startActivity(intent);*/
            }
        });

        if(usertype == 1) {
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = (int)view.getTag();
                    UserItem item = items.get(position);
                    parent.sendNotification(item.getUsername());
                    return false;
                }
            });
        }
    }

    public UserItem getItem(int pos) {
        return items.get(pos);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(UserItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<UserItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        @Bind(R.id.tv_username)
        TextView tvUserName;

        public UserViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }
}
