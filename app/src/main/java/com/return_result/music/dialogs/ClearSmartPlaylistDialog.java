package com.return_result.music.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.return_result.music.R;
import com.return_result.music.model.smartplaylist.AbsSmartPlaylist;

public class ClearSmartPlaylistDialog extends DialogFragment {

    @NonNull
    public static ClearSmartPlaylistDialog create(AbsSmartPlaylist playlist) {
        ClearSmartPlaylistDialog dialog = new ClearSmartPlaylistDialog();
        Bundle args = new Bundle();
        args.putParcelable("playlist", playlist);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //noinspection unchecked
        final AbsSmartPlaylist playlist = getArguments().getParcelable("playlist");
        int title = R.string.clear_playlist_title;
        //noinspection ConstantConditions
        CharSequence content = Html.fromHtml(getString(R.string.clear_playlist_x, playlist.name));

        return new MaterialDialog.Builder(getActivity())
                .title(title)
                .content(content)
                .positiveText(R.string.clear_action)
                .negativeText(android.R.string.cancel)
                .onPositive((dialog, which) -> {
                    if (getActivity() == null) {
                        return;
                    }
                    playlist.clear(getActivity());
                })
                .build();
    }
}
