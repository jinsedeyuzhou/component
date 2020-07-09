package com.ebrightmoon.common.ebus;



import com.ebrightmoon.common.binding.command.BindingAction;
import com.ebrightmoon.common.binding.command.BindingConsumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Messenger {

    private static Messenger defaultInstance;

    private HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> recipientsOfSubclassesAction;

    private HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> recipientsStrictAction;

    public static Messenger getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new Messenger();
        }
        return defaultInstance;
    }


    public static void overrideDefault(Messenger newWeakMessenger) {
        defaultInstance = newWeakMessenger;
    }

    public static void reset() {
        defaultInstance = null;
    }

    /**
     * @param recipient the receiver,if register in activity the recipient always set "this",
     *                  and "WeakMessenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                  you can also register with Activity context and also in onDestroy to unregister.
     * @param action    do something on message received
     */
    public void register(Object recipient, BindingAction action) {
        register(recipient, null, false, action);
    }

    /**
     * @param recipient                 the receiver,if register in activity the recipient always set "this",
     *                                  and "WeakMessenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                                  you can also register with Activity context and also in onDestroy to unregister.
     * @param receiveDerivedMessagesToo whether Derived class of recipient can receive the message
     * @param action                    do something on message received
     */
    public void register(Object recipient, boolean receiveDerivedMessagesToo, BindingAction action) {
        register(recipient, null, receiveDerivedMessagesToo, action);
    }

    /**
     * @param recipient the receiver,if register in activity the recipient always set "this",
     *                  and "WeakMessenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                  you can also register with Activity context and also in onDestroy to unregister.
     * @param token     register with a unique token,when a messenger send a msg with same token,it
     *                  will
     *                  receive this msg
     * @param action    do something on message received
     */
    public void register(Object recipient, Object token, BindingAction action) {
        register(recipient, token, false, action);
    }

    /**
     * @param recipient                 the receiver,if register in activity the recipient always set "this",
     *                                  and "WeakMessenger.getDefault().unregister(this)" in onDestroy,if in ViewModel,
     *                                  you can also register with Activity context and also in onDestroy to unregister.
     * @param token                     register with a unique token,when a messenger send a msg with same token,it
     *                                  will
     *                                  receive this msg
     * @param receiveDerivedMessagesToo whether Derived class of recipient can receive the message
     * @param action                    do something on message received
     */
    public void register(Object recipient, Object token, boolean receiveDerivedMessagesToo, BindingAction action) {

        java.lang.reflect.Type messageType = NotMsgType.class;

        HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> recipients;

        if (receiveDerivedMessagesToo) {
            if (recipientsOfSubclassesAction == null) {
                recipientsOfSubclassesAction = new HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>>();
            }

            recipients = recipientsOfSubclassesAction;
        } else {
            if (recipientsStrictAction == null) {
                recipientsStrictAction = new HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>>();
            }

            recipients = recipientsStrictAction;
        }

        java.util.List<WeakActionAndToken> list;

        if (!recipients.containsKey(messageType)) {
            list = new ArrayList<WeakActionAndToken>();
            recipients.put(messageType, list);
        } else {
            list = recipients.get(messageType);
        }

        WeakAction weakAction = new WeakAction(recipient, action);

        WeakActionAndToken item = new WeakActionAndToken(weakAction, token);
        list.add(item);
        cleanup();
    }

    /**
     * @param recipient {}
     * @param tClass    class of T
     * @param action    this action has one params that type of tClass
     * @param <T>       message data type
     */
    public <T> void register(Object recipient, Class<T> tClass, BindingConsumer<T> action) {
        register(recipient, null, false, action, tClass);
    }

    /**
     * see {}
     *
     * @param recipient                 receiver of message
     * @param receiveDerivedMessagesToo whether derived class of recipient can receive the message
     * @param tClass                    class of T
     * @param action                    this action has one params that type of tClass
     * @param <T>                       message data type
     */
    public <T> void register(Object recipient, boolean receiveDerivedMessagesToo, Class<T> tClass, BindingConsumer<T> action) {
        register(recipient, null, receiveDerivedMessagesToo, action, tClass);
    }

    /**
     * see {}
     *
     * @param recipient receiver of message
     * @param token     register with a unique token,when a messenger send a msg with same token,it
     *                  will
     *                  receive this msg
     * @param tClass    class of T for BindingConsumer
     * @param action    this action has one params that type of tClass
     * @param <T>       message data type
     */
    public <T> void register(Object recipient, Object token, Class<T> tClass, BindingConsumer<T> action) {
        register(recipient, token, false, action, tClass);
    }

    /**
     * see {}
     *
     * @param recipient                 receiver of message
     * @param token                     register with a unique token,when a messenger send a msg with same token,it
     *                                  will
     *                                  receive this msg
     * @param receiveDerivedMessagesToo whether derived class of recipient can receive the message
     * @param action                    this action has one params that type of tClass
     * @param tClass                    class of T for BindingConsumer
     * @param <T>                       message data type
     */
    public <T> void register(Object recipient, Object token, boolean receiveDerivedMessagesToo, BindingConsumer<T> action, Class<T> tClass) {

        java.lang.reflect.Type messageType = tClass;

        HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> recipients;

        if (receiveDerivedMessagesToo) {
            if (recipientsOfSubclassesAction == null) {
                recipientsOfSubclassesAction = new HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>>();
            }

            recipients = recipientsOfSubclassesAction;
        } else {
            if (recipientsStrictAction == null) {
                recipientsStrictAction = new HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>>();
            }

            recipients = recipientsStrictAction;
        }

        java.util.List<WeakActionAndToken> list;

        if (!recipients.containsKey(messageType)) {
            list = new ArrayList<WeakActionAndToken>();
            recipients.put(messageType, list);
        } else {
            list = recipients.get(messageType);
        }

        WeakAction weakAction = new WeakAction<T>(recipient, action);

        WeakActionAndToken item = new WeakActionAndToken(weakAction, token);
        list.add(item);
        cleanup();
    }


    private void cleanup() {
        cleanupList(recipientsOfSubclassesAction);
        cleanupList(recipientsStrictAction);
    }

    /**
     * @param token send with a unique token,when a receiver has register with same token,it will
     *              receive this msg
     */
    public void sendNoMsg(Object token) {
        sendToTargetOrType(null, token);
    }

    /**
     * send to recipient directly with has not any message
     *
     * @param target WeakMessenger.getDefault().register(this, ..) in a activity,if target set this
     *               activity
     *               it will receive the message
     */
    public void sendNoMsgToTarget(Object target) {
        sendToTargetOrType(target.getClass(), null);
    }

    /**
     * send message to target with token,when a receiver has register with same token,it will
     * receive this msg
     *
     * @param token  send with a unique token,when a receiver has register with same token,it will
     *               receive this msg
     * @param target send to recipient directly with has not any message,
     *               WeakMessenger.getDefault().register(this, ..) in a activity,if target set this activity
     *               it will receive the message
     */
    public void sendNoMsgToTargetWithToken(Object token, Object target) {
        sendToTargetOrType(target.getClass(), token);
    }

    /**
     * send the message type of T, all receiver can receive the message
     *
     * @param message any object can to be a message
     * @param <T>     message data type
     */
    public <T> void send(T message) {
        sendToTargetOrType(message, null, null);
    }

    /**
     * send the message type of T, all receiver can receive the message
     *
     * @param message any object can to be a message
     * @param token   send with a unique token,when a receiver has register with same token,it will
     *                receive this message
     * @param <T>     message data type
     */
    public <T> void send(T message, Object token) {
        sendToTargetOrType(message, null, token);
    }

    /**
     * send message to recipient directly
     *
     * @param message any object can to be a message
     * @param target  send to recipient directly with has not any message,
     *                WeakMessenger.getDefault().register(this, ..) in a activity,if target set this activity
     *                it will receive the message
     * @param <T>     message data type
     * @param <R>     target
     */
    public <T, R> void sendToTarget(T message, R target) {
        sendToTargetOrType(message, target.getClass(), null);
    }

    /**
     * Unregister the receiver such as:
     * WeakMessenger.getDefault().unregister(this)" in onDestroy in the Activity is required avoid
     * to
     * memory leak!
     *
     * @param recipient receiver of message
     */
    public void unregister(Object recipient) {
        unregisterFromLists(recipient, recipientsOfSubclassesAction);
        unregisterFromLists(recipient, recipientsStrictAction);
        cleanup();
    }


    public <T> void unregister(Object recipient, Object token) {
        unregisterFromLists(recipient, token, null, recipientsStrictAction);
        unregisterFromLists(recipient, token, null, recipientsOfSubclassesAction);
        cleanup();
    }


    private static <T> void sendToList(
            T message,
            java.util.Collection<WeakActionAndToken> list,
            java.lang.reflect.Type messageTargetType,
            Object token) {
        if (list != null) {
            // Clone to protect from people registering in a "receive message" method
            // Bug correction Messaging BL0004.007
            ArrayList<WeakActionAndToken> listClone = new ArrayList<>();
            listClone.addAll(list);

            for (WeakActionAndToken item : listClone) {
                WeakAction executeAction = item.getAction();
                if (executeAction != null
                        && item.getAction().isLive()
                        && item.getAction().getTarget() != null
                        && (messageTargetType == null
                        || item.getAction().getTarget().getClass() == messageTargetType
                        || classImplements(item.getAction().getTarget().getClass(), messageTargetType))
                        && ((item.getToken() == null && token == null)
                        || item.getToken() != null && item.getToken().equals(token))) {
                    executeAction.execute(message);
                }
            }
        }
    }

    private static void unregisterFromLists(Object recipient, HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists) {
        if (recipient == null
                || lists == null
                || lists.size() == 0) {
            return;
        }
        synchronized (lists) {
            for (java.lang.reflect.Type messageType : lists.keySet()) {
                for (WeakActionAndToken item : lists.get(messageType)) {
                    WeakAction weakAction = item.getAction();

                    if (weakAction != null
                            && recipient == weakAction.getTarget()) {
                        weakAction.markForDeletion();
                    }
                }
            }
        }
        cleanupList(lists);
    }

    private static <T> void unregisterFromLists(
            Object recipient,
            BindingConsumer<T> action,
            HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists,
            Class<T> tClass) {
        java.lang.reflect.Type messageType = tClass;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction<T> weakActionCasted = (WeakAction<T>) item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getBindingConsumer())) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }

    private static void unregisterFromLists(
            Object recipient,
            BindingAction action,
            HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists
    ) {
        java.lang.reflect.Type messageType = NotMsgType.class;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction weakActionCasted = (WeakAction) item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getBindingAction())) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }


    private static <T> void unregisterFromLists(
            Object recipient,
            Object token,
            BindingConsumer<T> action,
            HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists, Class<T> tClass) {
        java.lang.reflect.Type messageType = tClass;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction<T> weakActionCasted = (WeakAction<T>) item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getBindingConsumer())
                        && (token == null
                        || token.equals(item.getToken()))) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }

    private static void unregisterFromLists(
            Object recipient,
            Object token,
            BindingAction action,
            HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists) {
        java.lang.reflect.Type messageType = NotMsgType.class;

        if (recipient == null
                || lists == null
                || lists.size() == 0
                || !lists.containsKey(messageType)) {
            return;
        }

        synchronized (lists) {
            for (WeakActionAndToken item : lists.get(messageType)) {
                WeakAction weakActionCasted = (WeakAction) item.getAction();

                if (weakActionCasted != null
                        && recipient == weakActionCasted.getTarget()
                        && (action == null
                        || action == weakActionCasted.getBindingAction())
                        && (token == null
                        || token.equals(item.getToken()))) {
                    item.getAction().markForDeletion();
                }
            }
        }
    }

    private static boolean classImplements(java.lang.reflect.Type instanceType, java.lang.reflect.Type interfaceType) {
        if (interfaceType == null
                || instanceType == null) {
            return false;
        }
        Class[] interfaces = ((Class) instanceType).getInterfaces();
        for (Class currentInterface : interfaces) {
            if (currentInterface == interfaceType) {
                return true;
            }
        }

        return false;
    }

    private static void cleanupList(HashMap<java.lang.reflect.Type, java.util.List<WeakActionAndToken>> lists) {
        if (lists == null) {
            return;
        }
        for (Iterator it = lists.entrySet().iterator(); it.hasNext(); ) {
            Object key = it.next();
            java.util.List<WeakActionAndToken> itemList = lists.get(key);
            if (itemList != null) {
                for (WeakActionAndToken item : itemList) {
                    if (item.getAction() == null
                            || !item.getAction().isLive()) {
                        itemList.remove(item);
                    }
                }
                if (itemList.size() == 0) {
                    lists.remove(key);
                }
            }
        }
    }

    private void sendToTargetOrType(java.lang.reflect.Type messageTargetType, Object token) {
        Class messageType = NotMsgType.class;
        if (recipientsOfSubclassesAction != null) {
            // Clone to protect from people registering in a "receive message" method
            // Bug correction Messaging BL0008.002
//            var listClone = recipientsOfSubclassesAction.Keys.Take(_recipientsOfSubclassesAction.Count()).ToList();
            java.util.List<java.lang.reflect.Type> listClone = new ArrayList<>();
            listClone.addAll(recipientsOfSubclassesAction.keySet());
            for (java.lang.reflect.Type type : listClone) {
                java.util.List<WeakActionAndToken> list = null;

                if (messageType == type
                        || ((Class) type).isAssignableFrom(messageType)
                        || classImplements(messageType, type)) {
                    list = recipientsOfSubclassesAction.get(type);
                }

                sendToList(list, messageTargetType, token);
            }
        }

        if (recipientsStrictAction != null) {
            if (recipientsStrictAction.containsKey(messageType)) {
                java.util.List<WeakActionAndToken> list = recipientsStrictAction.get(messageType);
                sendToList(list, messageTargetType, token);
            }
        }

        cleanup();
    }

    private static void sendToList(
            java.util.Collection<WeakActionAndToken> list,
            java.lang.reflect.Type messageTargetType,
            Object token) {
        if (list != null) {
            // Clone to protect from people registering in a "receive message" method
            // Bug correction Messaging BL0004.007
            ArrayList<WeakActionAndToken> listClone = new ArrayList<>();
            listClone.addAll(list);

            for (WeakActionAndToken item : listClone) {
                WeakAction executeAction = item.getAction();
                if (executeAction != null
                        && item.getAction().isLive()
                        && item.getAction().getTarget() != null
                        && (messageTargetType == null
                        || item.getAction().getTarget().getClass() == messageTargetType
                        || classImplements(item.getAction().getTarget().getClass(), messageTargetType))
                        && ((item.getToken() == null && token == null)
                        || item.getToken() != null && item.getToken().equals(token))) {
                    executeAction.execute();
                }
            }
        }
    }

    private <T> void sendToTargetOrType(T message, java.lang.reflect.Type messageTargetType, Object token) {
        Class messageType = message.getClass();


        if (recipientsOfSubclassesAction != null) {
            // Clone to protect from people registering in a "receive message" method
            // Bug correction Messaging BL0008.002
//            var listClone = recipientsOfSubclassesAction.Keys.Take(_recipientsOfSubclassesAction.Count()).ToList();
            java.util.List<java.lang.reflect.Type> listClone = new ArrayList<>();
            listClone.addAll(recipientsOfSubclassesAction.keySet());
            for (java.lang.reflect.Type type : listClone) {
                java.util.List<WeakActionAndToken> list = null;

                if (messageType == type
                        || ((Class) type).isAssignableFrom(messageType)
                        || classImplements(messageType, type)) {
                    list = recipientsOfSubclassesAction.get(type);
                }

                sendToList(message, list, messageTargetType, token);
            }
        }

        if (recipientsStrictAction != null) {
            if (recipientsStrictAction.containsKey(messageType)) {
                java.util.List<WeakActionAndToken> list = recipientsStrictAction.get(messageType);
                sendToList(message, list, messageTargetType, token);
            }
        }

        cleanup();
    }

    private class WeakActionAndToken {
        private WeakAction action;
        private Object token;

        public WeakActionAndToken(WeakAction action, Object token) {
            this.action = action;
            this.token = token;
        }

        public WeakAction getAction() {
            return action;
        }

        public void setAction(WeakAction action) {
            this.action = action;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }
    }

    public static class NotMsgType {

    }
}
