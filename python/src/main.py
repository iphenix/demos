#!/usr/bin/python
# -*- coding: utf-8 -*-

import os
import threading
import re
import ConfigParser

import tornado.web
import tornado.ioloop

#import com.phenix.common.web

class Server(object):
    __instance_lock__ = threading.Lock()
    
    @staticmethod
    def instance():
        if not hasattr(Server, "__instance__"):
            with Server.__instance_lock__:
                if not hasattr(Server, "__instance__"):
                    # New instance after double check
                    Server.__instance__ = Server()
        return Server.__instance__
    
    def start(self):
        #pass
        tornado.ioloop.IOLoop.instance().start()

    def __init__(self, port=8080):
        self.port = port
        self.__initPath__()
        self.__initApp__()
    
    def __initPath__(self):
        filePath = os.path.realpath(__file__)
        self.ROOT = filePath[: filePath.rfind("/src")]
        self.SRC = os.path.join(self.ROOT, "src")
        self.RESOURCE = os.path.join(self.ROOT, "resource")
    
    def __initApp__(self):
        def _envParamReplace_(match):
            envParamName = match.groups()[0]
            if not hasattr(self, envParamName):
                return match.group()
            return getattr(self, envParamName)
        envParamRe = re.compile(r"\${(\w+)}", re.M)
        iniFile = os.path.join(self.RESOURCE, "server.ini")
        conf = ConfigParser.ConfigParser()
        conf.read(iniFile)
        settings = dict(conf.items("settings"))
        for key in settings.keys():
            val = settings[key]
            val = envParamRe.sub(_envParamReplace_, val)
            settings[key] = val
            
        handlers = []
        redirectSection = dict(conf.items("redirect"))
        for pair in redirectSection.items():
            handlers.append((pair[1], tornado.web.RedirectHandler, pair[0]))
            
        handlerSection = dict(conf.items("handler"))
        classParamRe = re.compile("^(.*)\.([^\_]+)", re.M)
        for pair in handlerSection.items():
            handlerClass = pair[1]
            match = classParamRe.match(handlerClass)
            
            module = __import__(match.groups()[0], fromlist=[""])
            handler = getattr(module, match.groups()[1])
            handlers.append((pair[0], handler))
            
        app = tornado.web.Application(handlers, **settings)
        app.listen(self.port)
        self.app = app

if __name__ == "__main__":
    def name(match):
        print match.groups()[0]
        return match.group()
    #Server.instance().start()
    s = "${ROOT}/www/${NAME}"
    r = re.compile(r"\${(\w+)}", re.M)
    #r.sub(name, s)
    print r.sub(name, s)
    
    p = re.compile('(a(b)c)d')
    m = p.match('abcd')
    print m.groups()
    
    s = "com.phenix.common.web.MainHandler"
    p = re.compile("^(.*)\.([^\_]+)", re.M)
    m = p.match(s)
    print m.groups()[0]
    p = re.compile(r"[.]")
    print p.split(m.groups()[0])
    print m.groups()[1]
    print __import__("com.phenix.common.web", fromlist=[""])
    
