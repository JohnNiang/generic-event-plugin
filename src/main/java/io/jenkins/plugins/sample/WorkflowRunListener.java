package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;

import java.io.IOException;

@Extension
public class WorkflowRunListener extends RunListener<WorkflowRun> {
    @Override
    public void onStarted(WorkflowRun run, TaskListener listener) {
        WorkflowRunEvent event = new WorkflowRunEvent();
        event.setBuildNumber(run.getNumber());
        try {
            event.setMultiBranchJob("org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject".equals(run.getParent().getParent().getClass().getName()));
        } catch (IllegalStateException e) {
            // don't care about this case
        }
        event.setJobName(run.getParent().getFullName());
        sendEvent(event);
    }

    private void sendEvent(WorkflowRunEvent event) {
        EventGlobalConfiguration config = EventGlobalConfiguration.get();
        String receiver = config.getReceiver();
        if (receiver == null || "".equals(receiver.trim())) {
            return;
        }

        HttpClient client = new HttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(receiver))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(JSONObject.fromObject(event).toString()))
//                .build();

        PostMethod request = new PostMethod(receiver);
        request.addParameter(new NameValuePair("event", JSONObject.fromObject(event).toString()));

        new Thread(() -> {
            try {
                client.executeMethod(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
