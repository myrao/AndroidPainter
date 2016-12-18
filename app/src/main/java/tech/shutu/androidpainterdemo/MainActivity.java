package tech.shutu.androidpainterdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.shutu.androidpainterdemo.view.DrawingCanvasView;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.dv_canvas)
    DrawingCanvasView dvCanvas;
    @BindView(R.id.sb_painter)
    SeekBar sbPainter;
    @BindView(R.id.btn_paint_panel)
    ImageButton btnPaintPanel;
    @BindView(R.id.iv_paint_brush_radius)
    ImageButton ivPaintBrushRadius;
    @BindView(R.id.iv_paint_color_picker)
    ImageButton ivPaintColorPicker;
    @BindView(R.id.iv_paint_redo)
    ImageButton ivPaintRedo;
    @BindView(R.id.iv_paint_undo)
    ImageButton ivPaintUndo;
    @BindView(R.id.iv_paint_recycler)
    ImageButton ivPaintRecycler;
    @BindView(R.id.ll_paint_item)
    LinearLayout llPaintItem;
    @BindView(R.id.rb_color_black)
    RadioButton rbColorBlack;
    @BindView(R.id.rb_color_red)
    RadioButton rbColorRed;
    @BindView(R.id.rb_color_yellow)
    RadioButton rbColorYellow;
    @BindView(R.id.rb_color_green)
    RadioButton rbColorGreen;
    @BindView(R.id.rb_color_blue)
    RadioButton rbColorBlue;
    @BindView(R.id.rg_colors)
    RadioGroup rgColors;
    @BindView(R.id.hsv_color_picker)
    HorizontalScrollView hsvColorPicker;

    private boolean isPainterPanelVisible = false;
    private boolean isSbPainterVisible = false;
    private boolean isColorPickerVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPainter();
        initColorPicker();
    }

    private void initColorPicker() {
        rgColors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_color_black:
                        dvCanvas.pickColor(getResources().getColor(R.color.material_light_black));
                        break;
                    case R.id.rb_color_red:
                        dvCanvas.pickColor(getResources().getColor(R.color.material_light_red));
                        break;
                    case R.id.rb_color_yellow:
                        dvCanvas.pickColor(getResources().getColor(R.color.material_light_yellow));
                        break;
                    case R.id.rb_color_green:
                        dvCanvas.pickColor(getResources().getColor(R.color.material_light_green));
                        break;
                    case R.id.rb_color_blue:
                        dvCanvas.pickColor(getResources().getColor(R.color.material_light_blue));
                        break;

                }
            }
        });
    }

    private void initPainter() {

        sbPainter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dvCanvas.setStrokeWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
        });

        sbPainter.setProgress(30);
    }

    @OnClick({R.id.btn_paint_panel, R.id.iv_paint_brush_radius, R.id.iv_paint_color_picker,
            R.id.iv_paint_redo, R.id.iv_paint_undo, R.id.iv_paint_recycler})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_paint_panel:
                isPainterPanelVisible = !isPainterPanelVisible;
                llPaintItem.setVisibility(isPainterPanelVisible ? View.VISIBLE : View.GONE);
                btnPaintPanel.setImageDrawable(getResources().getDrawable(
                        isPainterPanelVisible ? R.drawable.ic_paint_pencil_red : R.drawable.ic_paint_pencil_gray));
                dvCanvas.setEnablePainter(isPainterPanelVisible);
                break;
            case R.id.iv_paint_brush_radius:
                isSbPainterVisible = !isSbPainterVisible;
                sbPainter.setVisibility(isSbPainterVisible ? View.VISIBLE : View.GONE);
                break;
            case R.id.iv_paint_color_picker:
                isColorPickerVisible = !isColorPickerVisible;
                hsvColorPicker.setVisibility(isColorPickerVisible ? View.VISIBLE : View.GONE);
                break;
            case R.id.iv_paint_redo:
                break;
            case R.id.iv_paint_undo:
                dvCanvas.undo();
                break;
            case R.id.iv_paint_recycler:
                dvCanvas.clear();
                break;
        }
    }
}
