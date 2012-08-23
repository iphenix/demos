#!/usr/bin/python
# -*- coding: utf-8 -*-

import os
import threading

import tornado.web
import tornado.ioloop

import com.phenix.common.web

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
        tornado.ioloop.IOLoop.instance().start()

    def __init__(self, port=8080):
        self.port = port
        self.__initPath__()
        self.__initApp__()
    
    def __initPath__(self):
        filePath = os.path.realpath(__file__)
        self.ROOT = filePath[: filePath.rfind("src")]
    
    def __initApp__(self):
        handlers = [
                    (r"/", tornado.web.RedirectHandler, dict(url = "/static/index.html")),
                    (r"/main", com.phenix.common.web.MainHandler), 
                    ]
        settings = dict(debug = True,
                        static_path = os.path.join(self.ROOT, "www"), 
                        )
        app = tornado.web.Application(handlers, **settings)
        app.listen(self.port)
        self.app = app

if __name__ == "__main__":
    Server.instance().start()
