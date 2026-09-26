# Z3ter- Studio UI Contract

## Visual target

The supplied DaVinci Resolve reference screenshot is the PRIMARY visual target.

### Fidelity requirement: DaVinci-style workspace

Z3ter- is intended to reproduce the DaVinci Resolve-style editing workspace as closely as the target Android tablet/device permits. Do not redesign the workspace into a generic mobile editor and do not substitute a simplified card/grid UI.

The editor workspace must preserve the reference's overall composition and visual hierarchy:

- dark professional NLE chrome and panel surfaces
- top application/workspace bar
- left Media Pool / bins / media browser
- large central dual-viewer area
- right Inspector / audio / parameter area
- dense bottom multi-track timeline
- visible time ruler and frame/time positions
- red vertical playhead
- track headers and controls
- clip blocks with thumbnails
- audio clips with real waveforms
- compact professional transport/tool controls
- narrow separators and dense information layout
- proportional panel sizing matching the reference rather than large mobile cards

For tablet/desktop-sized displays, the goal is the closest practical visual reproduction of the reference composition, including panel placement, relative widths/heights, spacing density, typography hierarchy, controls, and timeline presentation.

On phones, the same workspace identity remains the target, but panels may responsively collapse or become navigable when the physical screen cannot contain the full composition. This is a layout adaptation, not permission to replace the editor with a different visual design.

### Important distinction

The UI may reproduce the visual organization and interaction model of the reference, but all editor behavior must remain Z3ter-/ClearCut's real implementation. Never draw fake clips, fake waveforms, fake playback, or fake controls merely to make a screenshot look correct.

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
