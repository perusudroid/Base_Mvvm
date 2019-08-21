package perusudroid.baseproject.data.model.api.response.stack;

import java.util.List;

/**
 * Awesome Pojo Generator
 */
public class StackResponse {
    private Integer quota_max;
    private Integer quota_remaining;
    private Boolean has_more;
    private List<Items> items;

    public void setQuota_max(Integer quota_max) {
        this.quota_max = quota_max;
    }

    public Integer getQuota_max() {
        return quota_max;
    }

    public void setQuota_remaining(Integer quota_remaining) {
        this.quota_remaining = quota_remaining;
    }

    public Integer getQuota_remaining() {
        return quota_remaining;
    }

    public void setHas_more(Boolean has_more) {
        this.has_more = has_more;
    }

    public Boolean getHas_more() {
        return has_more;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public List<Items> getItems() {
        return items;
    }
}