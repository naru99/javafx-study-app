package controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author naru99
 *
 */
public class DayDetailController extends AbstractController {

    public HBox timeBar;    // ２４時間バーオブジェクト（自動設定）

    public static VBox firstSelected;   // 選択開始時間

    private boolean[] checks = new boolean[48];     // 初期値falseで作成
    private boolean[] checks_bk = new boolean[48];  // バックアップ用

    /**
     * 詳細時間がマウスクリックされた時の処理
     *
     * @param mouseEvent
     */
    public void processMouseClick(MouseEvent mouseEvent) {

        System.out.println("shiftPressed : " + shiftPressed);

        // 選択されたBoxを取得
        VBox selected = (VBox) mouseEvent.getSource();
        // BoxのインデックスをIDから取得
        int idx = Integer.parseInt(selected.getId().substring(1));

        // シフトが押されている場合
        if(shiftPressed) {
            // 最初に選択されたBoxのインデックスを取得
            int fidx = Integer.parseInt(firstSelected.getId().substring(1));
            // ON/OFFのどちらに変更するかを取得
            boolean chg = checks_bk[fidx];

            int start = fidx + 1;
            int end = idx;

            //　最初に選択されたBoxより前のBoxが選択された場合
            if(idx < fidx) {
                start = idx;
                end = fidx - 1;
            }

            // バックアップをコピー
            boolean[] input = checks_bk.clone();
            for(int i = start; i <= end; i++) {
                // 変更後の状態の配列を作成
                input[i] = chg;
            }

            // 全てを変更する
            changeAll(input);

        // Shiftが押されていない場合
        }else {
            // 選択開始に設定
            firstSelected = selected;

            // 変更
            change(selected, idx);

            System.out.println("change " + checks[idx]);
            for(String style : selected.getStyleClass()) {
                System.out.println("style " + style);
            }


        }

    }

    /**
     * 保存ボタンクリック時の処理
     *
     * @param event
     */
    public void processSaveClick(ActionEvent event) {
        //
    }

    /**
     * Shiftキークリック時
     */
    public void shiftPress() {

        shiftPressed = true;

        // 現時点でのバックアップを取得
        checks_bk = checks.clone();

    }

    /**
     * Shiftキーリリース時
     */
    public void shiftRelease() {

        shiftPressed = false;

    }

    /**
     * Box変更
     *
     * @param input 変更後のリスト
     */
    private void changeAll(boolean[] input) {

        VBox wbox;  // ワーク用

        // 変更箇所を比較
        for (int i = 0; i < checks.length; i++) {

            // 変更対象の場合
            if (checks[i] != input[i]) {
                // 変更対象のBoxを取得
                wbox = (VBox) timeBar.getChildren().get(i);
                // 変更実行
                change(wbox, i);

            }
        }

    }

    /**
     * BoxのON/OFF反転
     *
     * @param box 対象Box
     * @param idx インデックス
     */
    private void change(VBox box, int idx) {

        // ON/OFFを切り替える
        checks[idx] = !checks[idx];

        // スタイルシートを切り替える
        box.getStyleClass().clear();
        if (checks[idx]) {
            box.getStyleClass().add("selectedCss");
        } else {
            box.getStyleClass().add("notSelectedCss");
        }

    }

}
