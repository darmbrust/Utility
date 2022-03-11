# !/usr/bin/env python3

#A little python script to manage using mencoder to record video from an IP source over rstp.
#This script starts mencoder, ensures it starts, retries if it fails to start, and restarts 
#mencoder if the process exits.  It also restarts mencoder once every 24 hours, recording to a new 
#file, and then deletes the old files, by default, files older than 14 days.

import subprocess
import sys
import time
import os
import signal
import datetime

STREAM_URL = "rtsp://username:password@ip:port/media/video1"
OUTPUT_PATH = "/opt/CameraRecord/"


def main():
    previousTask = None
    task = None
    sleepUntil = 0.0
    while not killer.kill_now:
        while (time.time() < sleepUntil and not killer.kill_now):
            time.sleep(1)
            if task != None and task.taskAccess.poll() != None:
                print("Stopped recording when not expected.  Restarting.")
                task = None
                break

        if (task != None):
            print("Cycling storage file " + str(datetime.datetime.now()))
            previousTask = task
            task = None

        if (killer.kill_now):
            break

        task = run()
        if (task == None):
            print("Failed to start - will sleep and try again " + str(datetime.datetime.now()))
            sleepUntil += time.time() + 60
        else:
            #Started ok
            nextAction = (datetime.date.today() + datetime.timedelta(days=1))
            nextAction = datetime.datetime.combine(
                nextAction, datetime.time(hour=0, minute=0, second=0, microsecond=0))
            sleepUntil = nextAction.timestamp()
            #Remove any MP4 files older than 14 days
            subprocess.run("ls " + OUTPUT_PATH + "20*.mp4 -1tr | head -n -14 | xargs -d '\n' rm -f --", shell=True)

        if previousTask != None:
            #Give the new recorder time to get up and going, before stopping the old one
            time.sleep(5)
            previousTask.stop()
            previousTask = None

    if (task != None):
        task.stop()
    if (previousTask != None):
        previousTask.stop()


def run():
    task = TaskManager()
    started = task.launch()
    if (started != True):
        task.stop()
        return None
    return task


class TaskManager:
    def __init__(self):

        self.pgid = None
        self.task = None

    @property
    def stdout(self):
        return self.task.stdout.read()

    @property
    def stderr(self):
        return self.task.stderr.read()

    @property
    def taskAccess(self):
        return self.task

    def launch(self):
        print("Starting recording " + str(datetime.datetime.now()))
        timeStamp = datetime.datetime.today().strftime('%Y-%m-%d-%H-%M-%S')
        self.task = subprocess.Popen(["mencoder", "-really-quiet", "-nocache", "-rtsp-stream-over-tcp",
                                    STREAM_URL, "-ovc", "copy", "-o", OUTPUT_PATH + timeStamp + ".mp4"],
                                     stdout=subprocess.PIPE,
                                     stderr=subprocess.PIPE,
                                     preexec_fn=os.setsid)
        self.pgid = os.getpgid(self.task.pid)
        return self.task.poll() == None

    def stop(self):
        try:
            print("Stopping task " + str(self.task.pid))
            self.task.terminate()
            i = 0
            while (self.task.poll() == None and i < 50):
                time.sleep(.1)
                i += 1

            if (self.task.poll() == None):
                print("mencoder running after terminate? " + str(self.pgid) + \
                      " code? " + str(self.task.poll()) + " " + str(self.task.returncode))
                raise PermissionError
        except PermissionError:
            print("Calling kill %d" % self.pgid)
            subprocess.check_call(["kill", "-1", str(self.pgid)])
            print("kill sent")


class GracefulKiller:
    kill_now = False

    def __init__(self):
        signal.signal(signal.SIGINT, self.exit_gracefully)
        signal.signal(signal.SIGTERM, self.exit_gracefully)

    def exit_gracefully(self, *args):
        self.kill_now = True


if __name__ == '__main__':
    killer = GracefulKiller()
    main()
    print("Exiting "  + str(datetime.datetime.now()))
