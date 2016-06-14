package controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import attend.Main;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author naru99
 *
 */
public class MainController extends AbstractController {

    /**
     * 画面部品（自動設定）
     */
    public StackPane stack;               // カレンダー本体
    public VBox calender;      // カレンダー日付部分

    // 部品のコントローラ（自動設定）
    public CalenderController calenderController;       // カレンダー
    public DayDetailController dayDetailController;     // 詳細入力

    // 表示中のカレンダー
    public VBox showCalender;

    /**
     * wss 表示中の年月
     */
    private int yyyy;
    private int mm;

    /**
     * 作業用変数
     */
    public Button dayButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        super.initialize(url, rb);

        Calendar cal = Calendar.getInstance();
        this.yyyy = cal.get(Calendar.YEAR);
        this.mm = cal.get(Calendar.MONTH) + 1;

//        DataReader reader = new DataReader();
//        reader.readData(yyyy);
        String str = "255";
        byte[] data = str.getBytes();

        for (byte b : data) {
            System.out.println(b);
        }

    }

    @Override
    public void start(Stage stage) {

        super.start(stage);

        // カレンダーの設定
        setCalenderDays();

        // キープレスイベントの追加
        stage.getScene().setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.isShiftDown()) {
                    dayDetailController.shiftPress();
            } else if (keyEvent.isControlDown()) {
                if(keyEvent.getText().equals("s")) {
                    // 保存処理
                }
            }

        });

        // キーリリースイベントの追加
        stage.getScene().setOnKeyReleased((KeyEvent keyEvent) -> {
            System.out.println("Key released $ shiftPressed :" + shiftPressed);
            if (shiftPressed && !keyEvent.isShiftDown()) {
                System.out.println("Shift released");
                dayDetailController.shiftRelease();
            }
        });

    }

    /**
     * 前へボタンクリック
     *
     * @param event
     */
    public void processBeforeClick(ActionEvent event) {

        // 日付を再設定
        if (mm == 1) {
            this.yyyy = yyyy - 1;
            this.mm = 12;
        } else {
            this.mm = mm - 1;
        }

        try {
            CalenderController main = (CalenderController) slideSceneContent(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 後ろへボタンクリック
     *
     * @param event
     */
    public void processAfterClick(ActionEvent event) {

        // 日付を再設定
        if (mm == 12) {
            this.yyyy = yyyy + 1;
            this.mm = 1;
        } else {
            this.mm = mm + 1;
        }

        try {

            CalenderController main = (CalenderController) slideSceneContent(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * カレンダー部分をスライドアニメーションで切り替える
     *
     * @param before 前方フラグ
     * @return
     * @throws IOException
     */
    protected Initializable slideSceneContent(Boolean before) throws IOException {

        int i = 1;

        // 進む方向を設定
        if (before) {
            i = -1;
        }

        // FXMLローダの生成
        FXMLLoader loader = new FXMLLoader();
        // FXMLの検索
        InputStream in = Main.class.getResourceAsStream(FXML_PATH + "/calender.fxml");
        // ビルダーの設定
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        // 相対パスの起点を設定
        loader.setLocation(Main.class.getResource(FXML_PATH));
        // ロード
        VBox nextCalender;
        try {
            nextCalender = (VBox) loader.load(in);
        } finally {
            in.close();
        }

        // 次のカレンダー用コントローラの取得
        CalenderController nextCtrl = (CalenderController) loader.getController();
        // カレンダーのコントローラーを入れ替え
        calenderController = nextCtrl;
        setCalenderDays();

        // 次のカレンダーを追加
        stack.getChildren().add(nextCalender);

        //新しいページを右からスライドさせるアニメーション
        TranslateTransition slidein
                = new TranslateTransition(new Duration(500));
        slidein.setNode(nextCalender);
        slidein.setFromX(800 * i);
        slidein.setToX(0);
        slidein.play();

        if (calender != null) {
            // 現在表示しているページがあれば、左にスライドさせる
            showCalender = calender;
            TranslateTransition slideout
                    = new TranslateTransition(new Duration(500));
            slideout.setNode(showCalender);
            slideout.setToX(-800 * i);
            slideout.setOnFinished((ActionEvent event) -> {
                // アニメーションが終了したら、シーングラフから削除する
                stack.getChildren().remove(showCalender);
            });
            slideout.play();
        }

        // カレンダーの参照の入れ替え
        calender = nextCalender;
        return (Initializable) loader.getController();
    }

    public void setCalenderDays() {

        System.out.println(yyyy + " " + mm);

        calenderController.setCalenderDays(this.yyyy, this.mm);

    }

}
