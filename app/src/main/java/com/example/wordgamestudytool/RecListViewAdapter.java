package com.example.wordgamestudytool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordgamestudytool.database.DB;
import com.example.wordgamestudytool.model.Input;

import java.util.ArrayList;

public class RecListViewAdapter extends RecyclerView.Adapter<RecListViewAdapter.ViewHolder> {

    private ArrayList<Input> inputs;

    public RecListViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_answer, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(inputs.get(position).getQuestion());
        holder.answer.setText(inputs.get(position).getAnswer());
        int index = holder.getAdapterPosition();
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB dataBase = DB.getInstance(v.getContext());
                Input toBeDeleted = inputs.remove(index);
                dataBase.inputDAO().delete(toBeDeleted);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<Input> inputs) {
        this.inputs = inputs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView question;
        private TextView answer;
        private ImageButton removeButton;
        private TextView line;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            removeButton = itemView.findViewById(R.id.removeButton);
            line = itemView.findViewById(R.id.line);
        }

    }
}
