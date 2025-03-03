package com.return_result.music.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;

import androidx.annotation.NonNull;

import com.return_result.music.R;
import com.return_result.music.loader.TopAndRecentlyPlayedSongsLoader;
import com.return_result.music.model.Song;
import com.return_result.music.provider.HistoryStore;

import java.util.List;

public class RecentlyPlayedPlaylist extends AbsSmartPlaylist {

    public RecentlyPlayedPlaylist(@NonNull Context context) {
        super(context.getString(R.string.recently_played), R.drawable.ic_history_white_24dp);
    }

    @NonNull
    @Override
    public List<Song> getSongs(@NonNull Context context) {
        return TopAndRecentlyPlayedSongsLoader.getRecentlyPlayedSongs(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        HistoryStore.getInstance(context).clear();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected RecentlyPlayedPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<RecentlyPlayedPlaylist> CREATOR = new Creator<RecentlyPlayedPlaylist>() {
        public RecentlyPlayedPlaylist createFromParcel(Parcel source) {
            return new RecentlyPlayedPlaylist(source);
        }

        public RecentlyPlayedPlaylist[] newArray(int size) {
            return new RecentlyPlayedPlaylist[size];
        }
    };
}
