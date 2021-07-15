package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filemanager.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Model.Folder;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder >{
    Context context;
    private ArrayList<Folder> listdata;
    private boolean mode;
    private OnItemClickListener listener;



    public HomeAdapter(Context context, ArrayList< Folder > list_folder , OnItemClickListener listener, boolean mode  ) {
        this.listdata =  list_folder ;
        this.context = context;
        this.listener = listener;
        this.mode = mode;

    }

    @NotNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.folder_view , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeAdapter.ViewHolder holder, int position) {
        final Folder folder = listdata.get(position);
        holder.txtName.setText(folder.getName());
        holder.txtDate.setText(folder.getCreat_time());
        holder.txtChild.setText(folder.getNum_child());
        holder.imageView.setImageResource(folder.getImage_avt()) ;

        if(this.mode == false )
        {

            // view check box
            holder.checkbox.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            layoutParams.setMargins(0,0,0,0);
            holder.imageView.setLayoutParams(layoutParams);
        }
        else
        {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
            layoutParams.setMargins(80,0,0,0);
            holder.imageView.setLayoutParams(layoutParams);

            holder.checkbox.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtName;
        TextView txtDate;
        TextView txtChild;
        ImageView imageView;
        CheckBox checkbox;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    listener.onItemClick(listdata.get(pos ));
                }
            });
              this.txtName = itemView.findViewById(R.id.txtName);
              this.txtChild = itemView.findViewById(R.id.txtChild);
              this.txtDate = itemView.findViewById(R.id.txtDate);
              this.imageView = itemView.findViewById(R.id.imgAvatar);
              this.checkbox = itemView.findViewById(R.id.checkbox_id);


            this.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //Code khi trạng thái check thay đổi

                    int pos = getAdapterPosition();
                    listener.onItemSelect(listdata.get(pos ),b );
             }
            });


        }



    }

}
