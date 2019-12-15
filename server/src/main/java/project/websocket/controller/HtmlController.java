package project.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.websocket.dao.ConnectionRepository;
import project.websocket.dao.MetadataRepository;

@Controller
@Transactional
public class HtmlController {


    @Autowired
    ConnectionRepository connections;

    @Autowired
    MetadataRepository metadataRepo;


    @GetMapping("/admin")
    public String AdminShow(Model model){

        int all = (int)metadataRepo.count();
        int all_con = (int)connections.count();
        int current_connections = WeShareHandler.sessions.size();

        model.addAttribute("all_msg", all);
        model.addAttribute("all_con",all_con);
        model.addAttribute("current_con",current_connections);
        return "admin";
    }



}
