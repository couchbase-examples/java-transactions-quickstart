package com.couchbase.example.controller;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryScanConsistency;
import com.couchbase.example.model.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private Cluster cluster;

    @Autowired
    private Bucket bucket;

    @GetMapping("/")
	public ModelAndView deleteBusinessIntelligence(ModelMap model) {

    try {
        List<DemoUser> users =  cluster.query("Select p.* from "+bucket.name()
                +"."+bucket.defaultScope().name()+"."
                +bucket.defaultCollection().name()+" p", QueryOptions.queryOptions()
                .scanConsistency(QueryScanConsistency.REQUEST_PLUS))
                .rowsAs(DemoUser.class);

        model.addAttribute("users", users);
        if (users.size() > 0) {
          return new ModelAndView("index");
        }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new ModelAndView("loading");

	}

}
