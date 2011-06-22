cd C:\Documents and Settings\sjain\Pay@Table\TestCases\ssl
echo =================EXPORTING CERTIFICATE FROM KEYSTORE============================
keytool -import -alias JUNIT -file Pay@Table_publicKey.cer -keystore Pay@Table_CLIENT.jks				
pause