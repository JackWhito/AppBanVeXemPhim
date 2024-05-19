package com.example.appbanvexemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.appbanvexemphim.Model.Seat;
import com.example.appbanvexemphim.R;

import java.util.ArrayList;

public class DatChoAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> values;
    private final ArrayList<Boolean> checkedList;
    private OnCheckedChangeListener listener;
    TextView txtHangGhe;

    int[]  lockedLists = new int[5];
    private static ArrayList<Seat> listSeated;

    public DatChoAdapter(Context context, ArrayList<String> values, ArrayList<Seat>listSeat) {
        super(context, R.layout.layout_item_seat, values);
        this.context = context;
        this.values = values;
        this.checkedList = new ArrayList<>();
        listSeated=listSeat;
        for (int i = 0; i < values.size(); i++) {
            checkedList.add(false);
        }


    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked, String nameSeat);
    }

    // Phương thức để thiết lập người nghe
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = convertView;
        ViewHolder holder;
        if (gridView == null) {
            gridView = inflater.inflate(R.layout.layout_item_seat, null);
            holder = new ViewHolder();
            //holder.checkBox = gridView.findViewById(R.id.check_box);
            //txtHangGhe=gridView.findViewById(R.id.txtHangGhe);
            gridView.setTag(holder);
        } else {
            holder = (ViewHolder) gridView.getTag();
        }

        // Kiểm tra xem vị trí có phải là một ô cần ẩn không
        int hangGhe=position/6;
        int soGhe=position%6;
        char hang='A';
        while (hangGhe>0)
        {
            hang++;
            hangGhe--;
        }
        if (position % 6 == 0) {
            holder.checkBox.setVisibility(View.INVISIBLE); // Ẩn checkbox
            holder.checkBox.setChecked(false); // Đặt trạng thái của checkbox thành false
            txtHangGhe.setText(values.get(position));
        } else {
            gridView.setVisibility(View.VISIBLE);
            holder.checkBox.setText(values.get(position));
            holder.checkBox.setChecked(checkedList.get(position));
            if (checkSeated(hang,soGhe)) {
                holder.checkBox.setVisibility(View.INVISIBLE);
            }
            holder.checkBox.setEnabled(true);


            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkedList.set(position, isChecked);
                    int hangGhe=position/6;
                    int soGhe=position%6;
                    char hang='A';
                    while (hangGhe>0)
                    {
                        hang++;
                        hangGhe--;
                    }
                    String s = String.valueOf(hang) +soGhe;
                    if (listener != null) {
                        listener.onCheckedChanged(isChecked,s);
                    }

                }
            });
        }

        return gridView;
    }

    static class ViewHolder {
        CheckBox checkBox;
    }
    private boolean checkSeated(char k, int a) {

        String s = String.valueOf(k) + a;
        for (Seat seat : listSeated) {
            if (seat.getName().equals(s)) {
                return true;
            }
        }
        return false;
    }

}
