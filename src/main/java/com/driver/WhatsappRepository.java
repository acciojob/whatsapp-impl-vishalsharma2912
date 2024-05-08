package com.driver;

import java.sql.Array;
import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    private HashMap<String,User> mobileUserDb = new HashMap<>();


    private HashMap<Group,List<User>> GroupUsersDb = new HashMap<>();

    // stores group and its adminUser
    private HashMap<Group,User> GroupAdminDb = new HashMap<>();


    // stores group , messages
   private HashMap<Group,List<Message>> MessageDb = new HashMap<>();
    private int countOfGroups=1;
    private int noOfMessage =1;

    public String createUser(String name , String mobile) throws Exception{
        if(mobileUserDb.containsKey(mobile)){
            throw new Exception("User already exists");
        }
        else {

          User user = new User();
          user.setName(name);
          user.setMobile(mobile);
            mobileUserDb.put(mobile,user);
            return "SUCCESS";
        }
    }
    public Group createGroup(List<User> users){
        Group gp = new Group();
        if(users.size()==2){
            gp.setName(users.get(1).getName());
            gp.setNumberOfParticipants(2);
        }
        else{

            gp.setName("Group "+countOfGroups);
            gp.setNumberOfParticipants(users.size());
            countOfGroups++;
        }
        GroupUsersDb.put(gp,users);
        GroupAdminDb.put(gp,users.get(0));
      return gp;
    }
    public int createMessage(String content){

        int id = noOfMessage ;

        Message message = new Message();
        noOfMessage = noOfMessage + 1;


        return id ;
    }
    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(GroupUsersDb.containsKey(group)){
            List<User> users = GroupUsersDb.get(group);
            if(users.contains(sender)){
                List<Message> msg;
                if(MessageDb.containsKey(group)){
                    msg=MessageDb.get(group);
                }
                else{
                   msg= new ArrayList<>();
                }
                msg.add(message);
                MessageDb.put(group,msg);
                return msg.size();
            }
            else{
                throw new Exception("You are not allowed to send message");
            }
        }
        else{
            throw new Exception("Group does not exist");
        }
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(GroupUsersDb.containsKey(group)){
            User admin = GroupAdminDb.get(group);
            if(admin.equals(approver)){
                List<User> users = GroupUsersDb.get(group);
                if(users.contains(user)){
                    admin=user;
                    GroupAdminDb.put(group,admin);
                    return "SUCCESS";
                }
                else{
                    throw new Exception("User is not a participant");
                }
            }
            else{
                throw new Exception("Approver does not have rights");
            }
        }
        else{
            throw new Exception("Group does not exist");
        }
    }
    public int removeUser(User user) throws Exception{


        return 0 ;


    }

    public String findMessage(Date start, Date end, int K) throws Exception{

        return "";

    }
}
