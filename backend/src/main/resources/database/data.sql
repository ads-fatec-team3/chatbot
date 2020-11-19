/* PRIVILÉGIOS */
INSERT INTO gruly_role_privileges (role_privilege_id, role_privilege_name) VALUES (1, 'SEND_MESSAGE');
INSERT INTO gruly_role_privileges (role_privilege_id, role_privilege_name) VALUES (2, 'ACCESS_GRULY');

/* PERMISSÕES */
INSERT INTO gruly_member_roles (member_role_id, member_role_name) VALUES (1, 'ROLE_DIRECTOR');
INSERT INTO gruly_member_roles (member_role_id, member_role_name) VALUES (2, 'ROLE_INSTRUCTOR');
INSERT INTO gruly_member_roles (member_role_id, member_role_name) VALUES (3, 'ROLE_ATTENDANT');
INSERT INTO gruly_member_roles (member_role_id, member_role_name) VALUES (4, 'ROLE_STUDENT');

/* PERMISSÃO E PRIVILÉGIO */
INSERT INTO gruly_member_role_privileges (role_privilege_id, member_role_id) VALUES (1, 1);
INSERT INTO gruly_member_role_privileges (role_privilege_id, member_role_id) VALUES (1, 2);
INSERT INTO gruly_member_role_privileges (role_privilege_id, member_role_id) VALUES (1, 3);
INSERT INTO gruly_member_role_privileges (role_privilege_id, member_role_id) VALUES (1, 4);
INSERT INTO gruly_member_role_privileges (role_privilege_id, member_role_id) VALUES (2, 4);

/* MEMBROS */
/* password = pass123 */
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (1, 'Diretor 1', 'diretor1', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (2, 'Atendente 1', 'atendente1', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (3, 'Estudante 1', 'estudante1', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (4, 'Estudante 2', 'estudante2', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (5, 'Instrutor 1', 'instrutor1', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');
INSERT INTO gruly_members (member_id, member_name, member_username, member_password)
VALUES (6, 'Instrutor 2', 'instrutor2', '$2y$12$/nsPZAvTSck1kYSNu8.PI.fDe20hQ1kjVRJgTnolhCci/w8pNrLUO');

/* PERMISSÃO E MEMBRO */
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (1, 1);
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (2, 3);
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (3, 4);
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (4, 4);
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (5, 2);
INSERT INTO gruly_member_role (member_id, member_role_id) VALUES (6, 2);

/* CONVERSAS */
INSERT INTO gruly_conversations (conversation_id, conversation_title) VALUES (1, 'PI');

/* CONVERSA E MEMBRO */
INSERT INTO gruly_member_conversations (conversation_id, member_id) VALUES (1, 1);
INSERT INTO gruly_member_conversations (conversation_id, member_id) VALUES (1, 3);
INSERT INTO gruly_member_conversations (conversation_id, member_id) VALUES (1, 5);

/* MENSAGENS */
INSERT INTO gruly_messages (message_id, message_text, message_timestamp, conversation_id, sender_id)
VALUES (0, 'oi', 'Wed Nov 18 02:05:34 GMT 2020', 1, 1);