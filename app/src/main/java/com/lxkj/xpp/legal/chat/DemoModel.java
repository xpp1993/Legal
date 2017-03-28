package com.lxkj.xpp.legal.chat;


import android.content.Context;

import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoModel {
    UserDao dao = null;
    protected Context context = null;
    protected Map<Key, Object> valueCache = new HashMap<Key, Object>();

    public DemoModel(Context ctx) {
        context = ctx;
        PreferenceManager.init(context);
    }

    public void setGroupsSynced(boolean synced) {
        PreferenceManager.getInstance().setGroupsSynced(synced);
    }
    /**
     * save current username
     * @param username
     */
    public void setCurrentUserName(String username){
        PreferenceManager.getInstance().setCurrentUserName(username);
    }

    public String getCurrentUsernName(){
        return PreferenceManager.getInstance().getCurrentUsername();
    }
    /**
     * 是否同步群组
     *
     * @return
     */
    public boolean isGroupsSynced() {
        return PreferenceManager.getInstance().isGroupsSynced();
    }

    /**
     * 同步联系人
     * @param synced
     */
    public void setContactSynced(boolean synced) {
        PreferenceManager.getInstance().setContactSynced(synced);
    }

    /**
     * 是否同步联系人
     * @return
     */
    public boolean isContactSynced() {
        return PreferenceManager.getInstance().isContactSynced();
    }

    /**
     * 同步黑名单
     * @param synced
     */
    public void setBlacklistSynced(boolean synced) {
        PreferenceManager.getInstance().setBlacklistSynced(synced);
    }

    /**
     * 是否同步黑名单
     * @return
     */
    public boolean isBacklistSynced() {
        return PreferenceManager.getInstance().isBacklistSynced();
    }

    /**
     * 获得群组的ids
     *
     * @return
     */
    public List<String> getDisabledGroups() {
        Object val = valueCache.get(Key.DisabledGroups);

        if (dao == null) {
            dao = new UserDao(context);
        }

        if (val == null) {
            val = dao.getDisabledGroups();
            valueCache.put(Key.DisabledGroups, val);
        }

        //noinspection unchecked
        return (List<String>) val;
    }

    /**
     * 设置用户ids
     *
     * @param ids
     */
    public void setDisabledIds(List<String> ids) {
        if (dao == null) {
            dao = new UserDao(context);
        }

        dao.setDisabledIds(ids);
        valueCache.put(Key.DisabledIds, ids);
    }

    /**
     * 获得用户Ids
     *
     * @return
     */
    public List<String> getDisabledIds() {
        Object val = valueCache.get(Key.DisabledIds);

        if (dao == null) {
            dao = new UserDao(context);
        }

        if (val == null) {
            val = dao.getDisabledIds();
            valueCache.put(Key.DisabledIds, val);
        }

        //noinspection unchecked
        return (List<String>) val;
    }

    /**
     * 设置消息是否有通知
     *
     * @param paramBoolean
     */
    public void setSettingMsgNotification(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgNotification(paramBoolean);
        valueCache.put(Key.VibrateAndPlayToneOn, paramBoolean);
    }

    /**
     * 获得消息是否有通知
     *
     * @return
     */
    public boolean getSettingMsgNotification() {
        Object val = valueCache.get(Key.VibrateAndPlayToneOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgNotification();
            valueCache.put(Key.VibrateAndPlayToneOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    /**
     * 设置消息是否有声音
     *
     * @param paramBoolean
     */
    public void setSettingMsgSound(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSound(paramBoolean);
        valueCache.put(Key.PlayToneOn, paramBoolean);
    }

    /**
     * 获得消息是否有声音
     *
     * @return
     */
    public boolean getSettingMsgSound() {
        Object val = valueCache.get(Key.PlayToneOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgSound();
            valueCache.put(Key.PlayToneOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    /**
     * 设置消息是否震动
     *
     * @param paramBoolean
     */
    public void setSettingMsgVibrate(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgVibrate(paramBoolean);
        valueCache.put(Key.VibrateOn, paramBoolean);
    }

    /**
     * 获得消息是否振动
     *
     * @return
     */
    public boolean getSettingMsgVibrate() {
        Object val = valueCache.get(Key.VibrateOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgVibrate();
            valueCache.put(Key.VibrateOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    /**
     * 设置消息的发送者
     *
     * @param paramBoolean
     */
    public void setSettingMsgSpeaker(boolean paramBoolean) {
        PreferenceManager.getInstance().setSettingMsgSpeaker(paramBoolean);
        valueCache.put(Key.SpakerOn, paramBoolean);
    }

    /**
     * 获得设置的消息发送者
     *
     * @return
     */
    public boolean getSettingMsgSpeaker() {
        Object val = valueCache.get(Key.SpakerOn);

        if (val == null) {
            val = PreferenceManager.getInstance().getSettingMsgSpeaker();
            valueCache.put(Key.SpakerOn, val);
        }

        return (Boolean) (val != null ? val : true);
    }

    /**
     * 获得联系人列表
     *
     * @return
     */
    public Map<String, EaseUser> getContactList() {
        UserDao dao = new UserDao(context);
        return dao.getContactList();
    }

    /**
     * 获得联系人列表(用户名小写)
     *
     * @return
     */
    public Map<String, RobotUser> getRobotList() {
        UserDao dao = new UserDao(context);
        return dao.getRobotUser();
    }

    /**
     * 保存某个联系人
     *
     * @param user
     */
    public void saveContact(EaseUser user) {
        UserDao dao = new UserDao(context);
        dao.saveContact(user);
    }

    /**
     * 保存列表联系人
     *
     * @param contactList
     * @return
     */
    public boolean saveContactList(List<EaseUser> contactList) {
        UserDao dao = new UserDao(context);
        dao.saveContactList(contactList);
        return true;
    }

    /**
     * 设置是否允许聊天室的Owner离开并删除聊天室的会话
     *
     * @return
     */
    public boolean isChatroomOwnerLeaveAllowed() {
        return PreferenceManager.getInstance().getSettingAllowChatroomOwnerLeave();
    }

    /**
     * 设置(主动或被动)退出群组时，是否删除群聊聊天记录
     *
     * @return
     */
    public boolean isDeleteMessagesAsExitGroup() {
        return PreferenceManager.getInstance().isDeleteMessagesAsExitGroup();
    }

    /**
     * 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
     *
     * @return
     */
    public boolean isAutoAcceptGroupInvitation() {
        return PreferenceManager.getInstance().isAutoAcceptGroupInvitation();
    }

    enum Key {
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
