package com.return_result.music.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kabouzeid.appthemehelper.util.ATHUtil;
import com.return_result.music.R;
import com.return_result.music.adapter.base.MediaEntryViewHolder;
import com.return_result.music.glide.ArtistGlideRequest;
import com.return_result.music.glide.SongGlideRequest;
import com.return_result.music.helper.MusicPlayerRemote;
import com.return_result.music.helper.menu.SongMenuHelper;
import com.return_result.music.model.Album;
import com.return_result.music.model.Artist;
import com.return_result.music.model.Song;
import com.return_result.music.util.MusicUtil;
import com.return_result.music.util.NavigationUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ALBUM = 1;
    private static final int ARTIST = 2;
    private static final int SONG = 3;

    private final AppCompatActivity activity;
    private List<Object> dataSet;

    public SearchAdapter(@NonNull AppCompatActivity activity, @NonNull List<Object> dataSet) {
        this.activity = activity;
        this.dataSet = dataSet;
    }

    public void swapDataSet(@NonNull List<Object> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position) instanceof Album) return ALBUM;
        if (dataSet.get(position) instanceof Artist) return ARTIST;
        if (dataSet.get(position) instanceof Song) return SONG;
        return HEADER;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER)
            return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.sub_header, parent, false), viewType);
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_list, parent, false), viewType);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ALBUM:
                final Album album = (Album) dataSet.get(position);
                holder.title.setText(album.getTitle());
                holder.text.setText(MusicUtil.getAlbumInfoString(activity, album));
                SongGlideRequest.Builder.from(Glide.with(activity), album.safeGetFirstSong())
                        .checkIgnoreMediaStore(activity).build()
                        .into(holder.image);
                break;
            case ARTIST:
                final Artist artist = (Artist) dataSet.get(position);
                holder.title.setText(artist.getName());
                holder.text.setText(MusicUtil.getArtistInfoString(activity, artist));
                ArtistGlideRequest.Builder.from(Glide.with(activity), artist)
                        .build().into(holder.image);
                break;
            case SONG:
                final Song song = (Song) dataSet.get(position);
                holder.title.setText(song.title);
                holder.text.setText(MusicUtil.getSongInfoString(song));
                break;
            default:
                holder.title.setText(dataSet.get(position).toString());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends MediaEntryViewHolder {
        public ViewHolder(@NonNull View itemView, int itemViewType) {
            super(itemView);
            itemView.setOnLongClickListener(null);

            if (itemViewType != HEADER) {
                itemView.setBackgroundColor(ATHUtil.resolveColor(activity, R.attr.cardBackgroundColor));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    itemView.setElevation(activity.getResources().getDimensionPixelSize(R.dimen.card_elevation));
                }
                if (shortSeparator != null) {
                    shortSeparator.setVisibility(View.GONE);
                }
            }

            if (menu != null) {
                if (itemViewType == SONG) {
                    menu.setVisibility(View.VISIBLE);
                    menu.setOnClickListener(new SongMenuHelper.OnClickSongMenu(activity) {
                        @Override
                        public Song getSong() {
                            return (Song) dataSet.get(getAdapterPosition());
                        }
                    });
                } else {
                    menu.setVisibility(View.GONE);
                }
            }

            switch (itemViewType) {
                case ALBUM:
                    setImageTransitionName(activity.getString(R.string.transition_album_art));
                    break;
                case ARTIST:
                    setImageTransitionName(activity.getString(R.string.transition_artist_image));
                    break;
                default:
                    View container = itemView.findViewById(R.id.image_container);
                    if (container != null) {
                        container.setVisibility(View.GONE);
                    }
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            Object item = dataSet.get(getAdapterPosition());
            switch (getItemViewType()) {
                case ALBUM:
                    NavigationUtil.goToAlbum(activity,
                            ((Album) item).getId(),
                            Pair.create(image,
                                    activity.getResources().getString(R.string.transition_album_art)
                            ));
                    break;
                case ARTIST:
                    NavigationUtil.goToArtist(activity,
                            ((Artist) item).getId(),
                            Pair.create(image,
                                    activity.getResources().getString(R.string.transition_artist_image)
                            ));
                    break;
                case SONG:
                    List<Song> playList = new ArrayList<>();
                    playList.add((Song) item);
                    MusicPlayerRemote.openQueue(playList, 0, true);
                    break;
            }
        }
    }
}
