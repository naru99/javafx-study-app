package attend;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author naru99
 *
 */
public class Main extends Application {

    private Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    private final String FXML_PATH = "/fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);

        gotoMain();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void gotoMain() {
        try {
            MainController main = (MainController) replaceSceneContent("main.fxml");
//            main.setStage(stage);
//            main.setCalenderDays();
            main.start(stage);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * stageに指定のFXMLの画面を設定し、関連するコントローラを返却する
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        // FXMLローダの生成
        FXMLLoader loader = new FXMLLoader();
        // FXMLの検索
        InputStream in = Main.class.getResourceAsStream(FXML_PATH + "/" + fxml);
        // ビルダーの設定
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        // 相対パスの起点を設定
        loader.setLocation(Main.class.getResource(FXML_PATH));

        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        // 作成したペインをTOPツリーとするシーンを作成
        Scene scene = new Scene(page, 800, 600);
        // 画面にシーンを設定
        stage.setScene(scene);
        // リサイズ
        stage.sizeToScene();
        // コントローラーを返却
        return (Initializable) loader.getController();
    }

    /**
     * 画面の部品を設定する
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    private Pane getParts(String fxml) throws Exception {
        Pane pane = null;
        // FXMLローダの生成
        FXMLLoader loader = new FXMLLoader();
        // FXMLの検索
        InputStream in = Main.class.getResourceAsStream(FXML_PATH + "/" + fxml);
        // ビルダーの設定
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        // 相対パスの起点を設定
        loader.setLocation(Main.class.getResource(FXML_PATH));

        try {
            pane = (Pane) loader.load(in);
        } finally {
            in.close();
        }

        return pane;
    }

}
