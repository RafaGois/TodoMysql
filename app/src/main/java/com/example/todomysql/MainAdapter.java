package com.example.todomysql;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Activity activity;
    JSONArray array;

    DataBaseHelser baseHelser;

    public MainAdapter (Activity activity, JSONArray array) {
        this.activity = activity;
        this.array = array;
    }

    public void updateArray (JSONArray array) {
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent, false );

        baseHelser = new DataBaseHelser(view.getContext());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        try {
            JSONObject object = array.getJSONObject(position);

            holder.tvText.setText(object.getString("text"));
            holder.tvData.setText(object.getString("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject object = array.getJSONObject(holder.getAdapterPosition());

                    String sId = object.getString("id");
                    String sText = object.getString("text");

                    Dialog dialog = new Dialog(activity);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dialog_main);
                    dialog.show();

                    EditText editText = dialog.findViewById(R.id.edit_text);
                    Button button = dialog.findViewById(R.id.botao);

                    editText.setText(sText);
                    button.setText("Update");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sText = editText.getText().toString().trim();

                            baseHelser.update(sId,sText);

                            updateArray(baseHelser.getArray());

                            notifyItemChanged(holder.getAdapterPosition());

                            dialog.dismiss();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                int position = holder.getAdapterPosition();
                try {
                    JSONObject object = array.getJSONObject(position);

                    String sID = object.getString("id");

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                    builder.setTitle("Confirmar");
                    builder.setMessage("Deseja realmente deletar a task ?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            baseHelser.delete(sID);
                            array.remove(position);

                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,array.length());
                        }
                    });

                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvText, tvData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tv);
            tvData = itemView.findViewById(R.id.tvsla);
        }
    }
}
