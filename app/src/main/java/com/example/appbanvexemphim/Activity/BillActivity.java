package com.example.appbanvexemphim.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanvexemphim.DAO.BillDAO;
import com.example.appbanvexemphim.DAO.MovieTypeDAO;
import com.example.appbanvexemphim.DAO.TransactionDAO;
import com.example.appbanvexemphim.Model.Bill;
import com.example.appbanvexemphim.Model.Cinema;
import com.example.appbanvexemphim.Model.CreateOrder;
import com.example.appbanvexemphim.Model.Movie;
import com.example.appbanvexemphim.Model.MovieType;
import com.example.appbanvexemphim.Model.Service;
import com.example.appbanvexemphim.Model.Show;
import com.example.appbanvexemphim.Model.Ticket;
import com.example.appbanvexemphim.Model.Transaction;
import com.example.appbanvexemphim.R;
import com.example.appbanvexemphim.Utils.Constant;
import com.example.appbanvexemphim.Utils.ImageConverter;
import com.vnpay.authentication.VNP_AuthenticationActivity;
import com.vnpay.authentication.VNP_SdkCompletedCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class BillActivity extends AppCompatActivity {
    private Movie movie;
    private Show show;
    private Cinema cinema;
    private Service service;
    private ArrayList<Ticket> tickets;
    private Bill bill = new Bill();
    private ImageButton ibtnBack;
    private Button btnContinue;
    private ImageView ivPicture;
    private TextView tvTitle, tvType, tvCinema, tvTime, tvTicket, tvPriceTicket, tvService, tvPriceService, tvTotal;
    private RadioGroup rdgPayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ZALO PAY
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2553, Environment.SANDBOX);
        if (Constant.user != null){
            getData();
            loadWidgets();
            loadData();
            createBill();
            addEvents();
        }
        else{
            Toast.makeText(this, "Bạn cần đăng nhập trước", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void createBill() {
        bill.setUser_id(Constant.user.getId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        bill.setCreate_date(dateFormat.format(new Date()));
        bill.setPrice(Constant.calculateTotal(tickets, service));
        bill.setState(0);
        bill.setPayment(Constant.payment.ZALOPAY.ordinal());
        BillDAO.getInstance(this).insert(bill);
        bill = BillDAO.getInstance(this).getLastBill();
        if (service != null)
            bill.setService_id(service.getId());
        for (Ticket t : tickets) {
            Transaction trans = new Transaction();
            trans.setBill_id(bill.getId());
            trans.setTicket_id(t.getId());
            TransactionDAO.getInstance(this).insert(trans);
        }
    }

    private void addEvents() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Thêm event thanh toán
                if (rdgPayment.getCheckedRadioButtonId() == R.id.rdbZALOPAY) {
                    openZALOPAY();
                } else {
                    Toast.makeText(BillActivity.this, "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openZALOPAY() {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder(Integer.parseInt(Constant.calculateTotal(tickets, service).toString().split("\\.")[0])+"");
            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(BillActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(BillActivity.this)
                                        .setTitle("Payment Success")
                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(BillActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }

                        });
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(BillActivity.this)
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(BillActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        new AlertDialog.Builder(BillActivity.this)
                                .setTitle("Payment Fail")
                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }
                });

                bill.setPayment(Constant.payment.ZALOPAY.ordinal());
                bill.setState(1);
                BillDAO.getInstance(this).update(bill);
                Toast.makeText(BillActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        ivPicture.setImageBitmap(ImageConverter.getImage(movie.getImage(), this));
        tvTitle.setText(movie.getName());
        tvType.setText(MovieTypeDAO.getInstance(this).getById(movie.getType_id()).getName() + " - " + movie.getRequireAge());
        tvCinema.setText(cinema.getName());
        tvTime.setText(show.getDatetime());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tvTicket.setText("Ghế: " + Constant.calculateSeatSelected(tickets));
        tvPriceTicket.setText(format.format(Constant.calculateTotal(tickets, null)));
        if (service != null) {
            tvService.setText("Dịch vụ: " + service.getName());
            tvPriceService.setText(format.format(service.getPrice()));
        }
        tvTotal.setText(format.format(Constant.calculateTotal(tickets, service)));
    }

    private void loadWidgets() {
        ibtnBack = (ImageButton) this.findViewById(R.id.ibtnBack);
        btnContinue = (Button) this.findViewById(R.id.btnContinue);
        ivPicture = (ImageView) findViewById(R.id.ivHistoryItemPicture);
        tvTitle = (TextView) findViewById(R.id.tvHistoryItemTitle);
        tvType = (TextView) findViewById(R.id.tvHistoryItemType);
        tvCinema = (TextView) findViewById(R.id.tvHistoryItemThreater);
        tvTime = (TextView) findViewById(R.id.tvHistoryItemDatetime);
        tvTicket = (TextView) findViewById(R.id.tvTicket);
        tvPriceTicket = (TextView) findViewById(R.id.tvPriceTicket);
        tvService = (TextView) findViewById(R.id.tvService);
        tvPriceService = (TextView) findViewById(R.id.tvPriceService);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        rdgPayment = (RadioGroup) this.findViewById(R.id.rdbPayment);
    }

    private void getData() {
        Intent i = getIntent();
        Bundle b = i.getBundleExtra("Data");
        assert b != null;
        movie = (Movie) b.getSerializable("movie");
        cinema = (Cinema) b.getSerializable("cinema");
        show = (Show) b.getSerializable("show");
        service = (Service) b.getSerializable("service");
        tickets = (ArrayList<Ticket>) b.getSerializable("tickets");

    }

    public void openVNPAY() {
        Intent intent = new Intent(this, VNP_AuthenticationActivity.class);
        intent.putExtra("url", "https://sandbox.vnpayment.vn/testsdk/"); //bắt buộc, VNPAY cung cấp
        intent.putExtra("tmn_code", "FAHASA03"); //bắt buộc, VNPAY cung cấp
        intent.putExtra("scheme", "resultactivity"); //bắt buộc, scheme để mở lại app khi có kết quả thanh toán từ mobile banking
        intent.putExtra("is_sandbox", false); //bắt buộc, true <=> môi trường test, true <=> môi trường live
        VNP_AuthenticationActivity.setSdkCompletedCallback(new VNP_SdkCompletedCallback() {
            @Override
            public void sdkAction(String action) {
                Log.wtf("SplashActivity", "action: " + action);
                //action == AppBackAction
                //Người dùng nhấn back từ sdk để quay lại

                //action == CallMobileBankingApp
                //Người dùng nhấn chọn thanh toán qua app thanh toán (Mobile Banking, Ví...)
                //lúc này app tích hợp sẽ cần lưu lại cái PNR, khi nào người dùng mở lại app tích hợp thì sẽ gọi kiểm tra trạng thái thanh toán của PNR Đó xem đã thanh toán hay chưa.

                //action == WebBackAction
                //Người dùng nhấn back từ trang thanh toán thành công khi thanh toán qua thẻ khi url có chứa: cancel.sdk.merchantbackapp

                //action == FaildBackAction
                //giao dịch thanh toán bị failed

                //action == SuccessBackAction
                //thanh toán thành công trên webview
            }
        });
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}