package controller;

import java.util.Calendar;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author naru99
 *
 */
public class CalenderController extends AbstractController {

    /**
     * 画面部品（自動設定）
     */
    public StackPane stack;               // カレンダー本体
    public VBox body;               // カレンダー本体
    public FlowPane calender;      // カレンダー日付部分
    public Text mTitle;             // 月タイトル

    /**
     * 選択中の日付ボタン
     */
    public Button selectedButton;

    /**
     * 表示中の年月
     */
    private int yyyy;
    private int mm;

    /**
     * 作業用変数
     */
    public Button dayButton;

    /**
     * カレンダーを設定
     */
    public void setCalenderDays(int yyyy, int mm) {

        int day;

        // 実行月取得
        Calendar cal = Calendar.getInstance();

        this.yyyy = yyyy;
        this.mm = mm;

        cal.set(Calendar.YEAR, yyyy);
        cal.set(Calendar.MONTH, mm - 1);
        cal.set(Calendar.DATE, 1);      // 1日を設定

        int week = cal.get(Calendar.DAY_OF_WEEK);           // 月初めの曜日を取得
        int last = cal.getActualMaximum(Calendar.DATE);     // 月末の日付を取得

        // 月タイトルを設定
        mTitle.setText(yyyy + " 年 " + mm + " 月");

        // 不要な日付の削除
        for (int i = 31; i > last; i--) {
            calender.getChildren().remove(i - 1);
        }

        try {
            // 土日のスタイル設定
            int weekCnt = week % 7;
            String dayId = null;

            for (Node node : calender.getChildren()) {
                dayButton = (Button) node;
                dayId = dayButton.getId();
                day = Integer.parseInt(dayId.replace("day", ""));

                if (weekCnt == 1) {
                    // 日曜のCSSの再設定
                    dayButton.getStyleClass().add("subBoxCss1");
                } else if (weekCnt == 0) {
                    // 土曜のCSS再設定
                    dayButton.getStyleClass().add("subBoxCss2");
                }

                weekCnt = (weekCnt + 1) % 7;
            }

            // １日の開始位置を調整
            VBox dayBox;
            for (int i = 1; i < week; i++) {
                dayBox = (VBox) getParts("box.fxml");
                calender.getChildren().add(0, dayBox);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 日付ボタンクリック
     *
     * @param event
     */
    public void processDayClick(ActionEvent event) {

        // 選択中のボタンがある場合
        if (selectedButton != null) {
            // クリック有効に変更
            selectedButton.setDisable(false);
        }

        selectedButton = (Button) event.getSource();
        System.out.println("Test " + selectedButton.getId());

        // クリック無効に変更
        selectedButton.setDisable(true);

    }

    public void processAfterClick(ActionEvent event) {

        // 選択中のボタンがある場合
        if (selectedButton != null) {
            selectedButton.setDisable(false);
        }

        selectedButton = (Button) event.getSource();
        System.out.println("Test " + selectedButton.getId());

        // クリック無効に変更
        selectedButton.setDisable(true);

    }

}
