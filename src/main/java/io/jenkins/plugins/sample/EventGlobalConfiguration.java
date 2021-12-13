package io.jenkins.plugins.sample;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;

@Extension(ordinal = 100)
@Symbol("eventDispatcher")
public class EventGlobalConfiguration extends GlobalConfiguration {
    private String receiver;

    public EventGlobalConfiguration() {
        this.load();
    }

    public static EventGlobalConfiguration get() {
        return GlobalConfiguration.all().get(EventGlobalConfiguration.class);
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) {
        req.bindJSON(this, json);
        this.save();
        return true;
    }

    @Override
    @Nonnull
    public String getDisplayName() {
        return "Event Dispatcher";
    }

    public String getReceiver() {
        return this.receiver;
    }

    @DataBoundSetter
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
