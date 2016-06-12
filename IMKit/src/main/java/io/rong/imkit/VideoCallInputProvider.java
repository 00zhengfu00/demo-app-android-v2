package io.rong.imkit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallSession;

public class VideoCallInputProvider extends InputProvider.ExtendProvider {
    ArrayList<String> allMembers;
    public VideoCallInputProvider(RongContext context) {
        super(context);
    }

    @Override
    public Drawable obtainPluginDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.rc_voip_icon_input_video);
    }

    @Override
    public CharSequence obtainPluginTitle(Context context) {
        return context.getString(R.string.rc_voip_video);
    }

    @Override
    public void onPluginClick(View view) {
        final Conversation conversation = getCurrentConversation();
        RongCallSession profile = RongCallClient.getInstance().getCallSession();
        if(profile != null && profile.getActiveTime() > 0) {
            Toast.makeText(getContext(), getContext().getString(R.string.rc_voip_call_start_fail), Toast.LENGTH_SHORT).show();
            return;
        }
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {
            Toast.makeText(getContext(), getContext().getString(R.string.rc_voip_call_network_error), Toast.LENGTH_SHORT).show();
            return;
        }
        if (conversation.getConversationType().equals(Conversation.ConversationType.PRIVATE)) {
            Intent intent = new Intent(RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEVIDEO);
            intent.putExtra("conversationType", conversation.getConversationType().getName().toLowerCase());
            intent.putExtra("targetId", conversation.getTargetId());
            intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().getApplicationContext().startActivity(intent);
        } else if (conversation.getConversationType().equals(Conversation.ConversationType.DISCUSSION)) {
            RongIM.getInstance().getDiscussion(conversation.getTargetId(), new RongIMClient.ResultCallback<Discussion>() {
                @Override
                public void onSuccess(Discussion discussion) {

                    Intent intent = new Intent(getContext(), CallSelectMemberActivity.class);
                    allMembers = (ArrayList<String>) discussion.getMemberIdList();
                    intent.putStringArrayListExtra("allMembers", allMembers);
                    String myId = RongIMClient.getInstance().getCurrentUserId();
                    ArrayList<String> invited = new ArrayList<>();
                    invited.add(myId);
                    intent.putStringArrayListExtra("invitedMembers", invited);
                    startActivityForResult(intent, 110);
                }

                @Override
                public void onError(RongIMClient.ErrorCode e) {

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }
        final Conversation conversation = getCurrentConversation();
        Intent intent = new Intent(RongVoIPIntent.RONG_INTENT_ACTION_VOIP_MULTIVIDEO);
        ArrayList<String> userIds = data.getStringArrayListExtra("invited");
        userIds.add(RongIMClient.getInstance().getCurrentUserId());
        intent.putExtra("conversationType", conversation.getConversationType().getName().toLowerCase());
        intent.putExtra("targetId", conversation.getTargetId());
        intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
        intent.putStringArrayListExtra("invitedUsers", userIds);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().getApplicationContext().startActivity(intent);
    }
}
