package com.dashboard.back.auth.controller.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dashboard.back.auth.model.testModel;

@Controller
@RequestMapping("/software")
public class SoftwareController {
    @GetMapping("csharp")
    public String csharp(Model model) {
        testModel testModel1 = testModel.builder().title("Title1").content("Content1").writeDate("WriteData1").build();
        testModel testModel2 = testModel.builder().title("Title2").content("Content2").writeDate("WriteData2").build();
        testModel testModel3 = testModel.builder().title("Title3").content("Content3").writeDate("WriteData3").build();
        testModel testModel4 = testModel.builder().title("Title4").content("Content4").writeDate("WriteData4").build();

        List<testModel> list = new ArrayList<testModel>();
        list.add(testModel1);
        list.add(testModel2);
        list.add(testModel3);
        list.add(testModel4);

        model.addAttribute("items", list);
        return "software/csharp";
    }

    @GetMapping("add")
    public String skillAdd() {
        return "software/addTemplate";
    }

    @GetMapping("{software}/{menuid}/{menudept}")
    public String software(Model menuModel, Model model, @PathVariable(value = "software") String software,
            @PathVariable(value = "menuid") String menuid, @PathVariable(value = "menudept") String menudept) {
        System.out.println("sortware : " + software + " / " + "menuid : " + menuid + " / " + "menudept : " + menudept);

        model.addAttribute("name", software);

        return "software/software";
    }
}
