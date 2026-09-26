# Z3ter- Studio UI Contract

## Visual target

The supplied reference image is the primary desktop/tablet editor composition target. It is a professional dark NLE workspace with these persistent regions:

1. **Top application bar**
   - compact project/title area
   - workspace/navigation controls
   - transport and utility controls
   - dark graphite chrome with thin separators

2. **Left media/project column**
   - project/media navigation
   - bin/category list
   - thumbnail media browser
   - lower project/effect area

3. **Center upper viewer**
   - large dual-viewer composition
   - source viewer on the left
   - program/timeline viewer on the right
   - black viewer background
   - transport controls directly below/around the viewers

4. **Right inspector/mixer column**
   - inspector controls
   - audio/mixer controls
   - parameter groups and sliders
   - scopes/graph area when enabled

5. **Bottom timeline**
   - time ruler with visible time/frame values
   - red playhead spanning the timeline
   - track headers and controls
   - video, overlay, text and audio tracks
   - clip thumbnails
   - audio waveforms
   - dense professional multi-track presentation

## Interaction requirement

The visual shell must never replace the real editor engine. Existing ClearCut operations remain the source of truth:

input -> EditorAction -> NLE engine -> persistence/playback/rendering

Touch:
- long press + drag clips
- edge trim
- pinch timeline zoom
- two-finger timeline navigation

Mouse:
- hover states
- left/right/middle click where useful
- wheel/trackpad scrolling
- modifier-wheel zoom
- drag/drop media and clips

Keyboard:
- Space playback
- Delete remove
- Ctrl/Cmd+Z undo
- Ctrl/Cmd+Shift+Z redo
- Ctrl/Cmd+S save
- arrows seek/nudge
- Home/End timeline navigation
- +/- or modifier-wheel timeline zoom

## Layout behavior

- On large tablets/desktop-like Android surfaces, preserve the five-region composition.
- The timeline remains the dominant lower workspace.
- The viewer remains the dominant upper-center workspace.
- Left and right columns stay visually separated by thin dividers.
- On smaller phones, collapse regions responsively rather than creating a fake desktop screenshot.
- All controls must call existing ViewModel/engine actions.

## Fidelity rule

Match the supplied reference composition, spacing hierarchy, dark graphite surfaces, panel boundaries, timeline density, viewer proportions, and control placement as closely as the target device dimensions permit. The screenshot is the visual reference; it is not a license to invent placeholder controls or fake timeline content.

## Functional rule

No placeholder playback, waveform, timeline, media, or export implementations. Existing real ClearCut engine behavior must remain intact while the presentation is rebuilt.
