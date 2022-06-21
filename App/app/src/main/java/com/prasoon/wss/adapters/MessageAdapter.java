package com.prasoon.wss.adapters;

import static android.content.Context.CLIPBOARD_SERVICE;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.prasoon.wss.R;
import com.prasoon.wss.models.Message;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private final List<Message> messages;
    private final Context context;

    public MessageAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.message.setText(message.getMessage());
        holder.datetime.setText(message.getCreatedAt().substring(0,10));

        View view = LayoutInflater.from(context).inflate(R.layout.save_message, null);
        TextView saveMessageTextView = view.findViewById(R.id.share_message);
        saveMessageTextView.setText(message.getMessage());

        holder.replyMessage.setOnClickListener(v->{
            Bitmap image = getBitmapFromView(view);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, "Story", null);
            Uri uri = Uri.parse(path);
            Intent feedIntent = new Intent(Intent.ACTION_SEND);
            feedIntent.setType("image/*");
            feedIntent.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(feedIntent, "Sharing Message"));

        });

        holder.copyMessage.setOnClickListener(v->{
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("message", message.getMessage());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Message Copied.", Toast.LENGTH_SHORT).show();
        });
    }

    public Bitmap getBitmapFromView(View v){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);

        return returnedBitmap;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView message, datetime;
        ImageView copyMessage, replyMessage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.messageText);
            datetime = itemView.findViewById(R.id.datetime);
            copyMessage = itemView.findViewById(R.id.copyMessage);
            replyMessage = itemView.findViewById(R.id.replyMessage);
        }
    }
}
