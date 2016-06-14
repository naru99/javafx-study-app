package controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import attend.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author naru99
 *
 */
public class AbstractController implements Initializable {

    public static Stage stage;
    protected final String FXML_PATH = "/fxml";

    protected static boolean shiftPressed = false;      //Shiftキーが押されているかどうか

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        stage.getScene().setOnKeyPressed((KeyEvent keyEvent) -> {
//            System.out.println(keyEvent.getText());
//        });
    }

    private void setStage(Stage stage) {
        this.stage = stage;
    }

    public void start(Stage stage) {
        setStage(stage);
    }

    /**
     * 画面の部品を設定する
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    protected Pane getParts(String fxml) throws Exception {
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

    /**
     * stageに指定のFXMLの画面を設定し、関連するコントローラを返却する
     *
     * @param fxml
     * @return
     * @throws Exception
     */
    protected Initializable replaceSceneContent(String fxml) throws Exception {
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

}
