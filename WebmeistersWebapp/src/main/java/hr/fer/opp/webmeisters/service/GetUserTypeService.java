package hr.fer.opp.webmeisters.service;

import hr.fer.opp.webmeisters.dao.OrganiserRepository;
import hr.fer.opp.webmeisters.dao.UserRepository;
import hr.fer.opp.webmeisters.dao.VisitorRepository;
import hr.fer.opp.webmeisters.data.model.Organiser;
import hr.fer.opp.webmeisters.data.model.User;
import hr.fer.opp.webmeisters.data.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class GetUserTypeService {

    public static String getType(User user){
        String r = "";
        if(user instanceof Organiser){
            r = "organiser";
        } else if(user instanceof Visitor) {
            r = "visitor";
        }
        return r;
    }
}
