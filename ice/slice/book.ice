[["java:package:com.ice.demo"]]
module book{
    sequence<bool> BoolSeq;

    struct Message{
        string name;
        int type;
        bool valid;
        double price;
        string content;
    };
    sequence<Message> MessageSeq;

    interface OnlineBook{
        Message bookTick(Message msg);
    };

    interface OfflineBook{
        BoolSeq bookTrance(MessageSeq msg);
    };
};
