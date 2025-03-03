package com.return_result.music.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.return_result.music.R;
import com.return_result.music.util.PlaylistsUtil;

public class RenamePlaylistDialog extends DialogFragment {

    private static final String PLAYLIST_ID = "playlist_id";

    @NonNull
    public static RenamePlaylistDialog create(long playlistId) {
        RenamePlaylistDialog dialog = new RenamePlaylistDialog();
        Bundle args = new Bundle();
        args.putLong(PLAYLIST_ID, playlistId);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long playlistId = getArguments().getLong(PLAYLIST_ID);
        return new MaterialDialog.Builder(getActivity())
                .title(R.string.rename_playlist_title)
                .positiveText(R.string.rename_action)
                .negativeText(android.R.string.cancel)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .input(getString(R.string.playlist_name_empty), PlaylistsUtil.getNameForPlaylist(getActivity(), playlistId), false,
                        (materialDialog, charSequence) -> {
                            final String name = charSequence.toString().trim();
                            if (!name.isEmpty()) {
                                long playlistId1 = getArguments().getLong(PLAYLIST_ID);
                                PlaylistsUtil.renamePlaylist(getActivity(), playlistId1, name);
                            }
                        })
                .build();
    }
}