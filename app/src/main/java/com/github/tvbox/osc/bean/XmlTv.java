package com.github.tvbox.osc.bean;

import android.text.TextUtils;
import androidx.base.x5.a;
import androidx.base.x5.d;
import androidx.base.x5.f;
import androidx.base.x5.o;
import androidx.media3.extractor.text.ttml.TtmlNode;
import java.util.Collections;
import java.util.List;
@o(name = "tv", strict = false)
public class XmlTv {
    @f(entry = "channel", inline = true, required = false)
    private List<Channel> channel;
    @f(entry = "programme", inline = true, required = false)
    private List<Programme> programme;

    @o(name = "channel")
    public static class Channel {
        @d(name = "display-name", required = false)
        private String displayName;
        @a(name = TtmlNode.ATTR_ID, required = false)
        private String id;

        public String getDisplayName() {
            return TextUtils.isEmpty(this.displayName) ? "" : this.displayName;
        }

        public String getId() {
            return TextUtils.isEmpty(this.id) ? "" : this.id;
        }
    }

    @o(name = "programme")
    public static class Programme {
        @a(name = "channel", required = false)
        private String channel;
        @a(name = TtmlNode.START, required = false)
        private String start;
        @a(name = "stop", required = false)
        private String stop;
        @d(name = "title", required = false)
        private String title;

        public String getChannel() {
            return TextUtils.isEmpty(this.channel) ? "" : this.channel;
        }

        public String getStart() {
            return TextUtils.isEmpty(this.start) ? "" : this.start;
        }

        public String getStop() {
            return TextUtils.isEmpty(this.stop) ? "" : this.stop;
        }

        public String getTitle() {
            return TextUtils.isEmpty(this.title) ? "" : this.title;
        }
    }

    public List<Channel> getChannel() {
        List<Channel> list = this.channel;
        return list == null ? Collections.emptyList() : list;
    }

    public List<Programme> getProgramme() {
        List<Programme> list = this.programme;
        return list == null ? Collections.emptyList() : list;
    }
}
