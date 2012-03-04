/**
 * 
 */
package com.huawei.pqm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author phenix
 *
 */
@Controller
@RequestMapping("/forum.do")
public class DemoController {
    @RequestMapping(params = "method=list") 
    public String listAllBoard() {
        System.out.println("call listAllBoard method.");
        return "demo";
    }
}
