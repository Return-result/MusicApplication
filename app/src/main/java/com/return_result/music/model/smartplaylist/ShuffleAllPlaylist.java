package com.return_result.music.model.smartplaylist;

import android.content.Context;
import android.os.Parcel;

import androidx.annotation.NonNull;

import com.return_result.music.R;
import com.return_result.music.loader.SongLoader;
import com.return_result.music.model.Song;

import java.util.List;

public class ShuffleAllPlaylist extends AbsSmartPlaylist {

    public ShuffleAllPlaylist(@NonNull Context context) {
        super(context.getString(R.string.action_shuffle_all), R.drawable.ic_shuffle_white_24dp);
    }

    @NonNull
    @Override
    public List<Song> getSongs(@NonNull Context context) {
        return SongLoader.getAllSongs(context);
    }

    @Override
    public void clear(@NonNull Context context) {
        // Shuffle all is not a real "Smart Playlist"
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ShuffleAllPlaylist(Parcel in) {
        super(in);
    }

    public static final Creator<ShuffleAllPlaylist> CREATOR = new Creator<ShuffleAllPlaylist>() {
        public ShuffleAllPlaylist createFromParcel(Parcel source) {
            return new ShuffleAllPlaylist(source);
        }

        public ShuffleAllPlaylist[] newArray(int size) {
            return new ShuffleAllPlaylist[size];
        }
    };
}
