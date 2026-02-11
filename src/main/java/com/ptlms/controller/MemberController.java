package com.ptlms.controller;

import com.ptlms.dto.WorkoutLogRequest;
import com.ptlms.entity.Member;
import com.ptlms.entity.WorkoutLog;
import com.ptlms.repository.MemberRepository;
import com.ptlms.service.WorkoutLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")  // ★ 회원만 접근 가능
public class MemberController {

    private final WorkoutLogService workoutLogService;
    private final MemberRepository memberRepository;

    // ★ 본인 운동 기록 조회
    @GetMapping("/workout/my")
    public ResponseEntity<List<WorkoutLog>> getMyWorkoutLogs(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = getMemberFromUser(userDetails);
        List<WorkoutLog> logs = workoutLogService.getMyWorkoutLogs(member.getId());
        return ResponseEntity.ok(logs);
    }

    // 달력용 기간별 조회
    @GetMapping("/workout/calendar")
    public ResponseEntity<List<WorkoutLog>> getWorkoutCalendar(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        Member member = getMemberFromUser(userDetails);
        List<WorkoutLog> logs = workoutLogService.getWorkoutLogsByDateRange(member.getId(), start, end);
        return ResponseEntity.ok(logs);
    }

    // ★ 오운완 등록
    @PostMapping("/workout")
    public ResponseEntity<WorkoutLog> createWorkoutLog(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody WorkoutLogRequest request) {
        Member member = getMemberFromUser(userDetails);
        WorkoutLog log = workoutLogService.createWorkoutLog(
                member.getId(),
                request.getWorkoutDate(),
                request.getWorkoutPart(),
                request.getWorkoutDetail(),
                request.getSets(),
                request.getDurationMinutes(),
                request.getDietPhotoUrl(),
                request.getDietMemo()
        );
        return ResponseEntity.ok(log);
    }

    // ★ 오운완 완료 처리
    @PostMapping("/workout/{logId}/complete")
    public ResponseEntity<WorkoutLog> completeWorkout(@PathVariable Long logId) {
        WorkoutLog log = workoutLogService.completeWorkout(logId);
        return ResponseEntity.ok(log);
    }

    private Member getMemberFromUser(UserDetails userDetails) {
        return memberRepository.findByUserId(
                memberRepository.findAll().stream()
                        .filter(m -> m.getUser().getEmail().equals(userDetails.getUsername()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."))
                        .getId()
        ).orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }
}
