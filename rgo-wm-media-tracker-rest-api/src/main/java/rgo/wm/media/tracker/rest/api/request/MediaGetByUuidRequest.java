package rgo.wm.media.tracker.rest.api.request;

import rgo.wm.common.utils.validator.constraint.UUIDPattern;

import java.util.UUID;

public class MediaGetByUuidRequest {

    @UUIDPattern
    private String uuid;

    public static MediaGetByUuidRequest of(String uuid) {
        var rq = new MediaGetByUuidRequest();
        rq.setUuid(uuid);
        return rq;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
