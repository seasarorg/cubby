SELECT m.member_id, m.member_name, m.full_name, m.web, m.biography, m.open, m.location, 
fm.request, c.comment, c.post_time 
FROM member m
LEFT OUTER JOIN
(SELECT mc.member_id, MAX(mc.comment_id) as comment_id
FROM member_comments mc
GROUP BY mc.member_id) recent
ON m.member_id = recent.member_id
LEFT OUTER JOIN comment c
ON recent.comment_id = c.comment_id
LEFT OUTER JOIN following_members fm
ON fm.member_id = /*dto.userMemberId*/1
AND fm.following_member_id = m.member_id
WHERE (m.member_name LIKE /*dto.keyword*/'%' OR m.full_name LIKE /*dto.keyword*/'%')
ORDER BY m.member_id
