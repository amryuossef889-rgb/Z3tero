package com.novacut.editor.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.novacut.editor.model.TrackType
import com.novacut.editor.ui.theme.ClearCutAccents
import com.novacut.editor.ui.theme.LocalClearCutColors


@Composable
fun DavinciTopBar(
    viewModel: EditorViewModel,
    projectName: String,
    onBack: () -> Unit,
    canUndo: Boolean,
    canRedo: Boolean
) {
    val colors = LocalClearCutColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .background(Color(0xFF121212))
            .padding(horizontal = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Z3ter-",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text("Media", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Cut", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Edit", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Fusion", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Color", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Fairlight", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Text("Deliver", color = colors.subtext, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 7.dp))
        Spacer(Modifier.weight(1f))
        Text(
            text = projectName,
            color = colors.text,
            fontSize = 10.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 260.dp)
        )
        Spacer(Modifier.width(8.dp))
        StudioIconButton("Undo", canUndo) { viewModel.undo() }
        StudioIconButton("Redo", canRedo) { viewModel.redo() }
        StudioIconButton("Add", true) { viewModel.showMediaPicker() }
        StudioIconButton("Export", true) { viewModel.showExportSheet() }
        StudioIconButton("Search", true) { viewModel.showCommandPalette() }
        StudioIconButton("Back", true) { onBack() }
    }
}

@Composable
private fun StudioIconButton(label: String, enabled: Boolean, onClick: () -> Unit) {
    val colors = LocalClearCutColors.current
    Surface(
        modifier = Modifier
            .size(width = 46.dp, height = 28.dp)
            .padding(horizontal = 2.dp)
            .clickable(enabled = enabled, onClick = onClick),
        color = Color.Transparent
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(label, color = if (enabled) colors.subtextStrong else Color(0xFF4A4A4A), fontSize = 8.sp)
        }
    }
}

@Composable
fun DavinciMediaPool(viewModel: EditorViewModel, modifier: Modifier = Modifier) {
    val colors = LocalClearCutColors.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val entries = remember(state.tracks) {
        state.tracks
            .flatMap { track -> track.clips.map { it to track.type } }
            .distinctBy { it.first.sourceUri.toString() }
    }

    Column(
        modifier = modifier.fillMaxHeight().background(Color(0xFF171717)).padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(32.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Film, null, tint = colors.text, modifier = Modifier.size(15.dp))
            Spacer(Modifier.width(7.dp))
            Text("Media Pool", color = colors.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.weight(1f))
            Text("${entries.size}", color = colors.subtext, fontSize = 10.sp)
        }
        Divider(color = Color(0xFF303030))
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            MediaPoolTab("Master", true)
            MediaPoolTab("Timeline 1", false)
        }
        Spacer(Modifier.height(8.dp))

        if (entries.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.Movie, null, tint = colors.subtext, modifier = Modifier.size(28.dp))
                Spacer(Modifier.height(8.dp))
                Text("No media", color = colors.subtext, fontSize = 11.sp)
                Spacer(Modifier.height(10.dp))
                Surface(
                    modifier = Modifier.clickable { viewModel.showMediaPicker() },
                    color = Color(0xFF2B2B2B),
                    shape = RoundedCornerShape(3.dp)
                ) {
                    Text("Import Media", color = colors.text, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(entries, key = { it.first.id }) { (clip, trackType) ->
                    val selected = state.selectedClipId == clip.id
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (selected) Color(0xFF303030) else Color(0xFF202020))
                            .border(1.dp, if (selected) ClearCutAccents.Blue else Color(0xFF2E2E2E), RoundedCornerShape(3.dp))
                            .clickable { viewModel.selectClip(clip.id) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().aspectRatio(1.55f).background(Color(0xFF0D0D0D)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.VideoFile,
                                contentDescription = null,
                                tint = colors.subtext,
                                modifier = Modifier.size(30.dp)
                            )
                            Icon(
                                when (trackType) {
                                    TrackType.VIDEO -> Icons.Default.Movie
                                    TrackType.AUDIO -> Icons.Default.MusicNote
                                    TrackType.OVERLAY -> Icons.Default.Image
                                    TrackType.TEXT -> Icons.Default.TextFields
                                    TrackType.ADJUSTMENT -> Icons.Default.Tune
                                },
                                null,
                                tint = Color.White.copy(alpha = 0.78f),
                                modifier = Modifier.align(Alignment.BottomStart).padding(4.dp).size(13.dp)
                            )
                        }
                        Text(
                            clip.sourceUri.lastPathSegment?.substringAfterLast('/') ?: "clip",
                            color = colors.text,
                            fontSize = 9.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MediaPoolTab(label: String, selected: Boolean) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(2.dp))
            .background(if (selected) Color(0xFF353535) else Color.Transparent)
            .padding(horizontal = 7.dp, vertical = 5.dp)
    ) {
        Text(label, color = if (selected) Color.White else Color(0xFF8F8F8F), fontSize = 9.sp)
    }
}

@Composable
fun DavinciInspector(viewModel: EditorViewModel, modifier: Modifier = Modifier) {
    val colors = LocalClearCutColors.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val selectedClip = remember(state.tracks, state.selectedClipId) {
        state.selectedClipId?.let { id ->
            state.tracks.asSequence().flatMap { it.clips.asSequence() }.firstOrNull { it.id == id }
        }
    }

    Column(
        modifier = modifier.fillMaxHeight().background(Color(0xFF171717)).padding(9.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().height(32.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Inspector", color = colors.text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.Tune, null, tint = colors.subtext, modifier = Modifier.size(15.dp))
        }
        Divider(color = Color(0xFF303030))
        Spacer(Modifier.height(8.dp))

        if (selectedClip == null) {
            InspectorSection("Project") {
                InspectorValue("Resolution", state.project.resolution.label)
                InspectorValue("Frame rate", "${state.project.frameRate} fps")
                InspectorValue("Duration", formatStudioTime(state.totalDurationMs))
            }
        } else {
            InspectorSection("Transform") {
                InspectorValue("Position", "0.00 / 0.00")
                InspectorValue("Zoom", "1.00")
                InspectorValue("Rotation", "0.00°")
            }
            InspectorSection("Cropping") {
                InspectorValue("Left", "0")
                InspectorValue("Right", "0")
                InspectorValue("Top", "0")
                InspectorValue("Bottom", "0")
            }
            InspectorSection("Retime and Scaling") {
                InspectorValue("Speed", "1.00x")
                InspectorValue("Scaling", "Fit")
            }
            InspectorSection("Audio") {
                InspectorValue("Volume", "0.0 dB")
                InspectorValue("Pan", "Center")
            }
            InspectorSection("Clip") {
                InspectorValue("Source", selectedClip.sourceUri.lastPathSegment?.substringAfterLast('/') ?: "clip")
                InspectorValue("Start", formatStudioTime(selectedClip.timelineStartMs))
                InspectorValue("Duration", formatStudioTime(selectedClip.timelineEndMs - selectedClip.timelineStartMs))
            }
        }
    }
}

@Composable
private fun InspectorSection(title: String, content: @Composable () -> Unit) {
    val colors = LocalClearCutColors.current
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
        Text(title.uppercase(), color = colors.subtext, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 5.dp))
        Surface(color = Color(0xFF202020), shape = RoundedCornerShape(3.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth().padding(7.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun InspectorAction(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    val colors = LocalClearCutColors.current
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(2.dp)).clickable(onClick = onClick).padding(horizontal = 6.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = colors.subtext, modifier = Modifier.size(14.dp))
        Spacer(Modifier.width(7.dp))
        Text(label, color = colors.text, fontSize = 10.sp)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.DragIndicator, null, tint = Color(0xFF686868), modifier = Modifier.size(13.dp))
    }
}

@Composable
private fun InspectorValue(label: String, value: String) {
    val colors = LocalClearCutColors.current
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 5.dp)) {
        Text(label, color = colors.subtext, fontSize = 9.sp, modifier = Modifier.weight(1f))
        Text(value, color = colors.text, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

private fun formatStudioTime(ms: Long): String {
    val totalSeconds = ms.coerceAtLeast(0L) / 1000L
    val h = totalSeconds / 3600L
    val m = (totalSeconds % 3600L) / 60L
    val s = totalSeconds % 60L
    return "%02d:%02d:%02d".format(h, m, s)
}
