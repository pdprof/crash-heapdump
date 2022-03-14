#!/bin/sh
vncserver -geometry 1900x1000
websockify --web=/usr/share/novnc 5801 localhost:5901
